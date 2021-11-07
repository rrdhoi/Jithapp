package com.pajokka.githubuser.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pajokka.githubuser.data.UserRepository
import com.pajokka.githubuser.ui.setting.SettingsViewModel
import com.pajokka.githubuser.ui.detail.DetailViewModel
import com.pajokka.githubuser.ui.detail.followers.FollowersViewModel
import com.pajokka.githubuser.ui.detail.following.FollowingViewModel
import com.pajokka.githubuser.ui.favorite.FavoriteViewModel
import com.pajokka.githubuser.ui.home.HomeViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val preference: SettingPreference
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, preference) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repository) as T
            }

            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                FollowingViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(preference) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}