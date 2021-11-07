package com.pajokka.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.pajokka.githubuser.data.source.local.LocalDataSource
import com.pajokka.githubuser.data.source.local.entity.UserEntity
import com.pajokka.githubuser.data.source.local.entity.UserModel
import com.pajokka.githubuser.data.source.remote.RemoteDataSource
import com.pajokka.githubuser.data.source.remote.StatusResponse
import com.pajokka.githubuser.vo.Resource
import kotlinx.coroutines.Dispatchers

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource,
) : UserDataSource {

    override suspend fun getUserByUsername(username: String): UserEntity? =
        localDataSource.getUserByUsername(username)


    override suspend fun deleteFavorite(username: String) {
        localDataSource.deleteFavorite(username)
    }

    override suspend fun addToFavorite(
        username: String,
        name: String,
        avatar: String,
        company: String,
        location: String,
        repository: String,
        follower: String,
        following: String
    ) {
        localDataSource.addToFavorite(
            username,
            name,
            avatar,
            company,
            location,
            repository,
            follower,
            following,
        )
    }

    override fun getAllFavoriteUser(): LiveData<List<UserEntity>>? =
        localDataSource.getAllFavoriteUser()

    override fun getUser(username: String) = liveData(Dispatchers.IO) {
        val listUserData = ArrayList<UserModel>()
        val users = remoteDataSource.getUser(username)
        emit(Resource.loading(data = null))
        users.let {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    emit(Resource.success(it.body))
                }
                StatusResponse.ERROR -> {
                    emit(Resource.error(it.message, listUserData))
                }
                StatusResponse.EMPTY -> {
                    emit(Resource.error(it.message, listUserData))
                }
            }
        }
    }

    override fun getFollowersUser(username: String) = liveData(Dispatchers.IO) {
        val listUserData = ArrayList<UserModel>()
        val users = remoteDataSource.getFollowersUser(username)
        emit(Resource.loading(data = null))
        users.let {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    emit(Resource.success(it.body))
                }
                StatusResponse.ERROR -> {
                    emit(Resource.error(it.message, listUserData))
                }
                StatusResponse.EMPTY -> {
                    emit(Resource.error(it.message, listUserData))
                }
            }
        }
    }

    override fun getFollowingUser(username: String) = liveData(Dispatchers.IO) {
        val listUserData = ArrayList<UserModel>()
        val users = remoteDataSource.getFollowingUser(username)
        emit(Resource.loading(data = null))
        users.let {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    emit(Resource.success(it.body))
                }
                StatusResponse.ERROR -> {
                    emit(Resource.error(it.message, listUserData))
                }
                StatusResponse.EMPTY -> {
                    emit(Resource.error(it.message, listUserData))
                }
            }
        }
    }


}