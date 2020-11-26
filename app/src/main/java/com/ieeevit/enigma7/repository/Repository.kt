package com.ieeevit.enigma7.repository

import androidx.lifecycle.LiveData
import com.ieeevit.enigma7.api.service.Api
import com.ieeevit.enigma7.database.*
import com.ieeevit.enigma7.model.LeaderboardEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val database: EnigmaDatabase) {

    val userDetails: LiveData<UserDetails> = database.userDao.getUserDetails()
    val questions: LiveData<Question> = database.questionsDao.getQuestion()
    val leaderBoard = database.leaderBoardDao.getLeaderBoard()
    val storyHistory = database.storyHistoryDao.getStoryHistory()

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
                user.points,
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

    suspend fun refreshLeaderBoard(authToken: String) {
        withContext(Dispatchers.IO) {
            val leaderboardEntry: ArrayList<LeaderboardEntry> = Api.retrofitService.getLeaderboard(authToken)
            database.leaderBoardDao.deleteAll()
            database.leaderBoardDao.insertLeaderBoard(leaderboardEntry.asDatabaseModel())
        }
    }

    suspend fun refreshStoryHistory(authToken: String, username: String) {
        withContext(Dispatchers.IO) {
            val completeStory = Api.retrofitService.getCompleteStory(authToken)
            var storyString = ""
            for (story in completeStory) {
                var text=""
                var word=""
                if (story.question_story != null) {
                    for (i:Char in story.question_story.story_text){
                        if(i!= ' ') word+=i
                        else{
                            when {
                                word.contains("<br>") -> {
                                    text+="\n"
                                    word=""
                                }
                                word.contains("<username>") -> {
                                    text+="\n$username: "
                                    word=""
                                }
                                word.contains("<4777>") -> {
                                    text+="\n4777: "
                                    word=""
                                }
                                else -> {
                                    text+="$word "
                                    word=""
                                }
                            }
                        }
                    }
                }
                val words=story.question_story.story_text.split(' ')
                text+=words[words.size-1]
                storyString+="$text\n\n"
            }
            val storyHistory = StoryHistory(1, storyString)
            database.storyHistoryDao.insertStoryHistory(storyHistory)
        }
    }
}
