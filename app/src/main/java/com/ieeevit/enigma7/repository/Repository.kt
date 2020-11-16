package com.ieeevit.enigma7.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.*
import com.ieeevit.enigma7.model.LeaderboardEntry
import com.ieeevit.enigma7.model.QuestionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val database: EnigmaDatabase) {

    val userDetails: LiveData<UserDetails> = database.userDao.getUserDetails()
    val questions: LiveData<Question> = database.questionsDao.getQuestion()
    val leaderBoard=database.leaderBoardDao.getLeaderBoard()
    suspend fun refreshUserDetails(authToken: String) {
        withContext(Dispatchers.IO) {
            val user = Api.retrofitService.getUserDetails(authToken)
            val userDetails = UserDetails(user.id, user.noOfHintsUsed, user.xp, user.rank, user.questionAnswered, user.email, user.username, user.points)
            database.userDao.insertUserDetails(userDetails)
        }
    }

    suspend fun clearCache() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }

    suspend fun refreshQuestion(authToken: String) {
        withContext(Dispatchers.IO) {
            Api.retrofitService.getQuestion(authToken).enqueue(object :Callback<QuestionResponse>{
                override fun onResponse(
                    call: Call<QuestionResponse>,
                    response: Response<QuestionResponse>
                ) {
                    val questionResponse= response.body()!!
                    val question =
                        Question(1, questionResponse.id, questionResponse.img_url, questionResponse.text)
                    database.questionsDao.insertQuestion(question)
                }

                override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                    Log.e("Failed",t.message!!)
                }
            })

        }
    }

    suspend fun refreshLeaderBoard(authToken: String) {
        withContext(Dispatchers.IO) {
            val leaderboardEntry: ArrayList<LeaderboardEntry> = Api.retrofitService.getLeaderboard(authToken)
            database.leaderBoardDao.insertLeaderBoard(leaderboardEntry.asDatabaseModel())
        }
    }


}