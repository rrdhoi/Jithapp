package com.pajokka.githubuser.data.source.remote

import com.pajokka.githubuser.data.source.local.entity.UserModel
import com.pajokka.githubuser.data.source.remote.network.ApiConfig
import com.pajokka.githubuser.data.source.remote.response.ApiResponse
import com.pajokka.githubuser.data.source.remote.response.DetailResponse

class RemoteDataSource {

    suspend fun getUser(username: String): ApiResponse<ArrayList<UserModel>> {
        val listUser = ArrayList<UserModel>()
        return try {
            val response = ApiConfig.provideApiService().getSearch(username)
            val responseItem = response.body()
            if (response.isSuccessful && responseItem != null) {
                for (item in responseItem.items) {
                    val users = ApiConfig.provideApiService().getDetailUser(item.login)
                        .body() as DetailResponse
                    val userData = UserModel(
                        users.login.toString(),
                        users.name.toString(),
                        users.avatarUrl.toString(),
                        users.company.toString(),
                        users.location.toString(),
                        users.publicRepos.toString(),
                        users.followers.toString(),
                        users.following.toString(),
                    )
                    listUser.add(userData)
                }
                ApiResponse.success(listUser)
            } else {
                ApiResponse.error("Data not Found", listUser)
            }
        } catch (e: Exception) {
            ApiResponse.error("$e", listUser)
        }
    }

    suspend fun getFollowersUser(username: String): ApiResponse<ArrayList<UserModel>> {
        val listUser = ArrayList<UserModel>()
        return try {
            val response = ApiConfig.provideApiService().getFollowers(username)
            val responseItem = response.body()
            if (response.isSuccessful && responseItem != null) {
                for (item in responseItem) {
                    val users = ApiConfig.provideApiService().getDetailUser(item.login)
                        .body() as DetailResponse
                    val userData = UserModel(
                        users.login.toString(),
                        users.name.toString(),
                        users.avatarUrl.toString(),
                        users.company.toString(),
                        users.location.toString(),
                        users.publicRepos.toString(),
                        users.followers.toString(),
                        users.following.toString(),
                    )
                    listUser.add(userData)
                }
                ApiResponse.success(listUser)
            } else {
                ApiResponse.error("Data not Found", listUser)
            }
        } catch (e: Exception) {
            ApiResponse.error("$e", listUser)
        }
    }

    suspend fun getFollowingUser(username: String): ApiResponse<ArrayList<UserModel>> {
        val listUser = ArrayList<UserModel>()
        return try {
            val response = ApiConfig.provideApiService().getFollowing(username)
            val responseItem = response.body()
            if (response.isSuccessful && responseItem != null) {
                for (item in responseItem) {
                    val users = ApiConfig.provideApiService().getDetailUser(item.login)
                        .body() as DetailResponse
                    val userData = UserModel(
                        users.login.toString(),
                        users.name.toString(),
                        users.avatarUrl.toString(),
                        users.company.toString(),
                        users.location.toString(),
                        users.publicRepos.toString(),
                        users.followers.toString(),
                        users.following.toString(),
                    )
                    listUser.add(userData)
                }
                ApiResponse.success(listUser)
            } else {
                ApiResponse.error("Data not Found", listUser)
            }
        } catch (e: Exception) {
            ApiResponse.error("$e", listUser)
        }
    }

}