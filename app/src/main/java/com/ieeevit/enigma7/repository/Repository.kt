package com.ieeevit.enigma7.repository

import androidx.lifecycle.LiveData
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.EnigmaDatabase
import com.ieeevit.enigma7.database.Hint
import com.ieeevit.enigma7.database.Question
import com.ieeevit.enigma7.database.UserDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val database: EnigmaDatabase) {

    val userDetails: LiveData<UserDetails> = database.userDao.getUserDetails()
    val questions: LiveData<Question> = database.questionsDao.getQuestion()
    suspend fun refreshUserDetails(authToken: String) {
        withContext(Dispatchers.IO) {
            val user = Api.retrofitService.getUserDetails(authToken)
            val userDetails = UserDetails(
                user.id,
                user.noOfHintsUsed,
                user.xp,
                user.rank,
                user.questionAnswered,
                user.email,
                user.username,
                user.points
            )
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
            val questionResponse = Api.retrofitService.getQuestion(authToken)
            val question =
                Question(1, questionResponse.id, questionResponse.img_url, questionResponse.text)
            database.questionsDao.insertQuestion(question)
        }
    }


}