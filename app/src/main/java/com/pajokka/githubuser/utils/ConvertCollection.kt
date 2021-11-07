package com.pajokka.githubuser.utils

import com.pajokka.githubuser.data.source.local.entity.UserEntity
import com.pajokka.githubuser.data.source.local.entity.UserModel

class ConvertCollection() {
    fun convertListToArraylist(list: List<UserEntity>): ArrayList<UserModel> {
        val listUsers = ArrayList<UserModel>()
        list.forEach {
            val userData = UserModel(
                it.username,
                it.name,
                it.avatar,
                it.company,
                it.location,
                it.repository,
                it.follower,
                it.following
            )

            listUsers.add(userData)
        }

        return listUsers
    }
}