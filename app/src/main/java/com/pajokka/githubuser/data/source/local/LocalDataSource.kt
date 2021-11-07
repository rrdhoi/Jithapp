package com.pajokka.githubuser.data.source.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.pajokka.githubuser.data.source.local.entity.UserEntity
import com.pajokka.githubuser.data.source.local.room.UserDao
import com.pajokka.githubuser.data.source.local.room.UserDatabase

class LocalDataSource(application: Application) {
    private val userDao: UserDao?

    init {
        val userDb = UserDatabase.getInstance(application)
        userDao = userDb.userDao()
    }

    suspend fun getUserByUsername(username: String) = userDao?.getUserByUsername(username)

    suspend fun addToFavorite(
        username: String,
        name: String,
        avatar: String,
        company: String,
        location: String,
        repository: String,
        follower: String,
        following: String,
    ) {
        val user = UserEntity(
            username,
            name,
            avatar,
            company,
            location,
            repository,
            follower,
            following
        )
        userDao?.insertUser(user)
    }

    suspend fun deleteFavorite(username: String) {
        userDao?.deleteUser(username)
    }

    fun getAllFavoriteUser(): LiveData<List<UserEntity>>? = userDao?.getAllUser()
}