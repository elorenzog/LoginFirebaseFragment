package com.mastersoft.loginfirebase.data.user

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllUsers: LiveData<List<User>> = userDao.readAllUsers()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}