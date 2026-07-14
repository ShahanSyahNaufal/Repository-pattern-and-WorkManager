package com.example.repositorypatternandworkmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.repositorypatternandworkmanager.data.local.TaskEntity
import com.example.repositorypatternandworkmanager.ui.theme.RepositoryPatternAndWorkManagerTheme
import com.example.repositorypatternandworkmanager.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RepositoryPatternAndWorkManagerTheme {
                val viewModel: TaskViewModel = viewModel()
                val tasks by viewModel.tasks.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { viewModel.manualRefresh() }) {
                            Text(text = "Refresh", modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    }
                ) { innerPadding ->
                    TaskList(
                        tasks = tasks,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<TaskEntity>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(items = tasks, key = { it.id }) { task ->
            TaskItem(task)
            HorizontalDivider()
        }
    }
}

@Composable
fun TaskItem(task: TaskEntity) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = task.title, style = MaterialTheme.typography.titleMedium)
        Text(text = task.description, style = MaterialTheme.typography.bodySmall)
        Text(
            text = if (task.isCompleted) "Completed" else "Pending",
            color = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
}
