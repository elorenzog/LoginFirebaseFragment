package com.mastersoft.loginfirebase.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    val userid: String,
    val name: String,
    val last_name: String,
    val phone_number: String,
    val sex: String,
    val date_of_birth: String,
    val country: String,
    val province: String,
    val address: String
    )