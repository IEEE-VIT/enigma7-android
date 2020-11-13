package com.ieeevit.enigma7.database

import android.content.Context
import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_details")
data class UserDetails constructor(
    @PrimaryKey
    val id: Int?,
    val noOfHintsUsed: Int?,
    val xp: Int?,
    val rank: Int?,
    val questionAnswered: Int?,
    val email: String?,
    val username: String?,
    val points: Int?
)

@Entity(tableName = "hint")
data class Hint constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hint: String?
)
@Entity(tableName = "questions")
data class Question constructor(
    @PrimaryKey
    val primKey:Int?,
    val id: Int?,
    val img_url: String?,
    val text: String?
)


@Database(entities = [UserDetails::class, Hint::class,Question::class], version = 1, exportSchema = false)
abstract class EnigmaDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val hintDao: HintDao
    abstract val questionsDao:QuestionsDao
}

private lateinit var INSTANCE: EnigmaDatabase

fun getDatabase(context: Context): EnigmaDatabase {
    synchronized(EnigmaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EnigmaDatabase::class.java,
                "database"
            ).build()
        }
    }
    return INSTANCE
}
