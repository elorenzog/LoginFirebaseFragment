package com.mastersoft.loginfirebase.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val user_id: String,
    val name: String,
    val description: String,
    val completed: Boolean
)