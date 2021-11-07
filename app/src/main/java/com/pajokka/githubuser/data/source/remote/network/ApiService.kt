package com.pajokka.githubuser.data.source.remote.network

import com.pajokka.githubuser.BuildConfig
import com.pajokka.githubuser.data.source.remote.response.DetailResponse
import com.pajokka.githubuser.data.source.remote.response.FollowResponseItem
import com.pajokka.githubuser.data.source.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("/search/users")
    suspend fun getSearch(@Query("q") user: String): Response<SearchResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("/users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): Response<DetailResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("/users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<FollowResponseItem>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<FollowResponseItem>>

}