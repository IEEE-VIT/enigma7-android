package com.ieeevit.enigma7.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ieeevit.enigma7.database.getDatabase
import com.ieeevit.enigma7.repository.Repository
import retrofit2.HttpException

class RefreshXpWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "com.ieeevit.enigma7.work.RefreshXpWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = Repository(database)
        val authToken=inputData.getString("auth_token")!!
        try {
            repository.refreshUserDetails(authToken)
            Log.i("WorkManager","Work request for sync is run")
        } catch (e: HttpException) {
            Log.i("Exception",e.toString())
            return Result.retry()
        }
        return Result.success()
    }
}