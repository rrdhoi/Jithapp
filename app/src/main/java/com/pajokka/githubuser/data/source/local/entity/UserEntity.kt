package com.pajokka.githubuser.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    var username:String = "",

    @ColumnInfo(name = "name")
    var name:String= "",

    @ColumnInfo(name = "avatar")
    var avatar: String = "",

    @ColumnInfo(name = "company")
    var company: String = "",

    @ColumnInfo(name = "location")
    var location: String = "",

    @ColumnInfo(name = "repository")
    var repository: String = "",

    @ColumnInfo(name = "follower")
    var follower: String = "",

    @ColumnInfo(name = "following")
    var following: String = "",
): Serializable, Parcelable