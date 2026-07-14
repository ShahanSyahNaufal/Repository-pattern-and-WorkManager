package com.example.repositorypatternandworkmanager

import android.content.Context
import com.example.repositorypatternandworkmanager.data.local.AppDatabase
import com.example.repositorypatternandworkmanager.data.remote.ApiService
import com.example.repositorypatternandworkmanager.data.repository.TaskRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(ApiService::class.java)

    val repository = TaskRepository(database.taskDao(), apiService)
}
