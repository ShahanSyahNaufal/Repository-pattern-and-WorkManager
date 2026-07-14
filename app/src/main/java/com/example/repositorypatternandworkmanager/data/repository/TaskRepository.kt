package com.example.repositorypatternandworkmanager.data.repository

import android.util.Log
import com.example.repositorypatternandworkmanager.data.local.TaskDao
import com.example.repositorypatternandworkmanager.data.local.TaskEntity
import com.example.repositorypatternandworkmanager.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val apiService: ApiService
) {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun refreshTasks() {
        try {
            Log.d("TaskRepository", "Refreshing tasks...")
            val response = apiService.getTasks()
            Log.d("TaskRepository", "Fetched ${response.size} tasks")
            val entities = response.map {
                TaskEntity(it.id, it.title, "Task #${it.id}", it.completed)
            }
            taskDao.deleteAll()
            taskDao.insertAll(entities)
            Log.d("TaskRepository", "Database updated")
        } catch (e: Exception) {
            Log.e("TaskRepository", "Error refreshing tasks", e)
        }
    }
}
