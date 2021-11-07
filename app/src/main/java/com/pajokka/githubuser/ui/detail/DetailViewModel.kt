package com.pajokka.githubuser.ui.detail

import androidx.lifecycle.ViewModel
import com.pajokka.githubuser.data.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)
    suspend fun deleteFavorite(username: String) = userRepository.deleteFavorite(username)
    suspend fun addToFavorite(
        username: String,
        name: String,
        avatar: String,
        company: String,
        location: String,
        repository: String,
        follower: String,
        following: String,
    ) = userRepository.addToFavorite(
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