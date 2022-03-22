package com.mastersoft.loginfirebase.data.task

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {
    val readAllTasks: LiveData<List<Task>> = taskDao.readAllTasks()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }
}