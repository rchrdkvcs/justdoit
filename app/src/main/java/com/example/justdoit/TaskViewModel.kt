package com.example.justdoit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<Task>> = dao.getAllTasks()

    fun insert(task: Task) = viewModelScope.launch {
        dao.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        dao.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        dao.delete(task)
    }
}
