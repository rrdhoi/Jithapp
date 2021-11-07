package com.pajokka.githubuser.data

import androidx.lifecycle.LiveData
import com.pajokka.githubuser.data.source.local.entity.UserEntity
import com.pajokka.githubuser.data.source.local.entity.UserModel
import com.pajokka.githubuser.vo.Resource

interface UserDataSource {
    suspend fun getUserByUsername(username: String): UserEntity?
    suspend fun deleteFavorite(username: String)
    suspend fun addToFavorite(username: String,
                      name: String,
                      avatar: String,
                      company: String,
                      location: String,
                      repository: String,
                      follower: String,
                      following: String,)

    fun getAllFavoriteUser(): LiveData<List<UserEntity>>?

    fun getFollowersUser(username: String): LiveData<Resource<out ArrayList<UserModel>>>

    fun getUser(username: String):  LiveData<Resource<out ArrayList<UserModel>>>

    fun getFollowingUser(username: String): LiveData<Resource<out ArrayList<UserModel>>>
}