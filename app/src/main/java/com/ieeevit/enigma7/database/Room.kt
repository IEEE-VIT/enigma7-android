package com.ieeevit.enigma7.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("select * from user_details ")
    fun getUserDetails(): LiveData<UserDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetails(userDetails: UserDetails)
}

@Dao
interface QuestionsDao{
    @Query("select * from questions")
    fun getQuestion():LiveData<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question:Question)
}

@Dao
interface LeaderBoardDao{
    @Query("select * from leader_board")
    fun getLeaderBoard():LiveData<List<Leaderboard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeaderBoard(leaderboard: List<Leaderboard>)
}

@Dao
interface StoryHistoryDao{
    @Query("select * from story_history")
    fun getStoryHistory():LiveData<StoryHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStoryHistory(storyHistory: StoryHistory)
}




