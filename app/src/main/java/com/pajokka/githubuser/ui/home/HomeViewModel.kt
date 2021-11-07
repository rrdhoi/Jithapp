package com.pajokka.githubuser.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pajokka.githubuser.data.UserRepository
import com.pajokka.githubuser.utils.SettingPreference

class HomeViewModel(private val userRepository: UserRepository,private val pref: SettingPreference) : ViewModel() {
    fun getUser(username: String) = userRepository.getUser(username)
    fun getThemeSettings(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()
}