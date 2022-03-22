package com.mastersoft.loginfirebase.data.user
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun readAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE userid LIKE :id")
    suspend fun readOneUser(id: String): LiveData<List<User>>
}