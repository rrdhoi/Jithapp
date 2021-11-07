package com.pajokka.githubuser.ui.detail.followers

import androidx.lifecycle.ViewModel
import com.pajokka.githubuser.data.UserRepository

class FollowersViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFollowersUser(username: String) = userRepository.getFollowersUser(username)
}


