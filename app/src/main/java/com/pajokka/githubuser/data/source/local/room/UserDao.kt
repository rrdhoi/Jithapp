package com.pajokka.githubuser.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pajokka.githubuser.data.source.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteUser(username: String)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String) : UserEntity
}