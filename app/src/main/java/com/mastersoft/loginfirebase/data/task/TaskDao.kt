package com.mastersoft.loginfirebase.data.task

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun readAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :idTask ")
    fun readOneTask(idTask: Int): Task

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}