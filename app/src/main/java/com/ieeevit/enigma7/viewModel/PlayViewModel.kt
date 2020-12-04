package com.ieeevit.enigma7.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.model.*
import com.ieeevit.enigma7.repository.Repository
import com.ieeevit.enigma7.work.RefreshXpWorker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class PlayViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(getDatabase(application))
    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String>
        get() = _hint

    private val _story = MutableLiveData<Story>()
    val story: LiveData<Story>
        get() = _story

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _answerResponse = MutableLiveData<CheckAnswerResponse>()
    val answerResponse: LiveData<CheckAnswerResponse>
        get() = _answerResponse
    private val _powerUpStatus = MutableLiveData<UserStatus>()
    val powerUpStatus: LiveData<UserStatus>
        get() = _powerUpStatus
    val error = MutableLiveData<Int>()
    val skipStatus = MutableLiveData<Int>()
    val closeAnswerStatus = MutableLiveData<Int>()

    init {
        skipStatus.value = 0
        _hint.value = null
    }

    val questionResponse = repository.questions
    val userDetails = repository.userDetails

    fun getHint(authToken: String) {

        Api.retrofitService.getHint(authToken).enqueue(object : Callback<HintResponse> {
            override fun onResponse(call: Call<HintResponse>, response: Response<HintResponse>) {
                if (response.body() != null) {
                    val hintResponse: HintResponse? = response.body()
                    _hint.value = hintResponse?.hint
                } else {
                    error.value = 1
                }
            }

            override fun onFailure(call: Call<HintResponse>, t: Throwable) {
                _hint.value = ""
                error.value=1

            }
        })
    }

    fun checkAnswer(authToken: String, answer: String) {
        Api.retrofitService.checkAnswer(authToken, CheckAnswerRequest(answer))
            .enqueue(object : Callback<CheckAnswerResponse> {
                override fun onResponse(
                    call: Call<CheckAnswerResponse>,
                    response: Response<CheckAnswerResponse>
                ) {
                    if (response.body() != null) {
                        _answerResponse.value = response.body()
                    }
                }

                override fun onFailure(call: Call<CheckAnswerResponse>, t: Throwable) {
                    error.value=1
                }

            })
    }

    fun refreshUserDetailsFromRepository(authToken: String) {
        viewModelScope.launch {
            try {
                repository.refreshUserDetails(authToken)
            } catch (e: Exception) {

            }
        }
    }

    fun getPowerUpStatus(authToken: String) {
        Api.retrofitService.getPowerupStatus(authToken).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.body() != null) {
                    val userDetails = response.body()
                    _powerUpStatus.value = userDetails?.userStatus
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                error.value=1
            }
        })
    }

    fun startXpRetrieval(authToken: String) {
        GlobalScope.launch {
            setRecurringWork(authToken)
        }
    }

    private fun setRecurringWork(authToken: String) {
        val data = Data.Builder()
        data.putString("auth_token", authToken)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshXpWorker>(
            1,
            TimeUnit.HOURS
        ).setInputData(data.build()).setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshXpWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    fun refreshQuestionsFromRepository(authToken: String) {
        viewModelScope.launch {
            try {
                repository.refreshQuestion(authToken)

            } catch (e: Exception) {
                error.value = 1

            }
        }
    }

    fun usePowerUpCloseAnswer(authToken: String, answer: CloseAnswer) {
        Api.retrofitService.usePowerUpCloseAnswer(authToken, answer)
            .enqueue(object : Callback<PowerupResponse> {
                override fun onResponse(
                    call: Call<PowerupResponse>,
                    response: Response<PowerupResponse>
                ) {
                    if (response.body() != null && response.body()!!.detail != null) {
                        if (response.body()?.detail?.equals("The answer isn't a close answer")!! ||
                            response.body()?.detail?.equals("Insufficient Xp")!!
                        )
                            _status.value = response.body()?.detail
                    }
                    else if(response.body()==null){
                        _status.value="Try again"
                    }
                    else {
                        _status.value = "Close answer accepted"
                    }
                }

                override fun onFailure(call: Call<PowerupResponse>, t: Throwable) {
                    error.value=1
                }
            })
    }

    fun usePowerUpSkip(authToken: String) {
        Api.retrofitService.usePowerUpSkip(authToken).enqueue(object : Callback<PowerupResponse> {
            override fun onResponse(
                call: Call<PowerupResponse>,
                response: Response<PowerupResponse>
            ) {
                if (response.body() != null) {
                    if (response.body()!!.question_id > 0) {
                        refreshQuestionsFromRepository(authToken)
                        startXpRetrieval(authToken)
                        skipStatus.value = 1
                    } else {
                        _status.value = response.body()!!.detail
                    }
                }

            }

            override fun onFailure(call: Call<PowerupResponse>, t: Throwable) {
                error.value=1
            }
        })
    }

    fun usePowerUpHint(authToken: String) {
        Api.retrofitService.usePowerUpHint(authToken).enqueue(object : Callback<PowerupResponse> {
            override fun onResponse(
                call: Call<PowerupResponse>,
                response: Response<PowerupResponse>
            ) {
                if (response.body() != null) {
                    if (response.body()?.detail != null) {
                        if (response.body()?.detail?.equals("You have already taken a hint .")!! ||
                            response.body()?.detail?.equals("Insufficient Xp")!!
                        ) {
                            _status.value = response.body()?.detail
                        }
                    } else {
                        _hint.value = response.body()?.hint
                        startXpRetrieval(authToken)
                    }

                }

            }

            override fun onFailure(call: Call<PowerupResponse>, t: Throwable) {
                error.value=1
            }
        })
    }

    fun getStory(authToken: String) {
        Api.retrofitService.getStory("Token $authToken").enqueue(object : Callback<Story> {
            override fun onResponse(call: Call<Story>, response: Response<Story>) {
                if (response.body() != null) {
                    _story.value = response.body()
                }

            }

            override fun onFailure(call: Call<Story>, t: Throwable) {
                error.value=1
            }
        })
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlayViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlayViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}