package com.pajokka.githubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pajokka.githubuser.data.UserRepository
import com.pajokka.githubuser.data.source.local.entity.UserEntity

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getAllFavoriteUser(): LiveData<List<UserEntity>>? = userRepository.getAllFavoriteUser()

}