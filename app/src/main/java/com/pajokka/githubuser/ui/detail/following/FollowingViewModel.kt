package com.pajokka.githubuser.ui.detail.following

import androidx.lifecycle.ViewModel
import com.pajokka.githubuser.data.UserRepository

class FollowingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFollowingUser(username: String) = userRepository.getFollowingUser(username)
}