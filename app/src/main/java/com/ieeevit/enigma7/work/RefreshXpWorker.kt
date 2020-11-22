package com.ieeevit.enigma7.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.repository.Repository
import retrofit2.HttpException

class RefreshXpWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "com.ieeevit.enigma7.work.RefreshXpWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = Repository(database)
        val authToken = inputData.getString("auth_token")!!
        try {
            repository.refreshUserDetails(authToken)

        } catch (e: HttpException) {

            return Result.retry()
        }
        return Result.success()
    }
}