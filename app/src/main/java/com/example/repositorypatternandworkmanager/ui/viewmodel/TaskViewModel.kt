package com.example.repositorypatternandworkmanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.repositorypatternandworkmanager.TaskApplication
import com.example.repositorypatternandworkmanager.data.local.TaskEntity
import com.example.repositorypatternandworkmanager.data.repository.TaskRepository
import com.example.repositorypatternandworkmanager.worker.RefreshDataWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = (application as TaskApplication).container.repository
    val tasks: StateFlow<List<TaskEntity>> = repository.allTasks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val refreshRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "RefreshDataWork",
            ExistingPeriodicWorkPolicy.KEEP,
            refreshRequest
        )
    }

    fun manualRefresh() {
        viewModelScope.launch {
            repository.refreshTasks()
        }
    }
}
