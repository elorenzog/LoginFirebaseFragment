package com.mastersoft.loginfirebase.data.task

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun readAllTasks(): LiveData<List<Task>>
}