package com.example.repositorypatternandworkmanager

import android.app.Application

class TaskApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
