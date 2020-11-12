package com.ieeevit.enigma7.database

import android.content.Context
import androidx.room.*
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
    val hint: String
)


@Database(entities = [UserDetails::class, Hint::class], version = 1, exportSchema = false)
abstract class EnigmaDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val hintDao: HintDao
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
