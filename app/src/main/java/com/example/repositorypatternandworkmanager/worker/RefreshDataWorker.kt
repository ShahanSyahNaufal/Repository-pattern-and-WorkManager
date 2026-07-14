package com.example.repositorypatternandworkmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.repositorypatternandworkmanager.data.local.AppDatabase
import com.example.repositorypatternandworkmanager.data.remote.ApiService
import com.example.repositorypatternandworkmanager.data.repository.TaskRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RefreshDataWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d("RefreshDataWorker", "Starting background data refresh")

        val database = AppDatabase.getDatabase(applicationContext)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val repository = TaskRepository(database.taskDao(), apiService)

        return try {
            repository.refreshTasks()
            Log.d("RefreshDataWorker", "Background refresh successful")
            Result.success()
        } catch (e: Exception) {
            Log.e("RefreshDataWorker", "Background refresh failed", e)
            Result.retry()
        }
    }
}
