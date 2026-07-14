package com.example.repositorypatternandworkmanager.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getTasks(): List<TaskDto>
}
