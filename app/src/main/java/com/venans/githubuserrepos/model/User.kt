package com.venans.githubuserrepos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.venans.githubuserrepos.model.User.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class User (

    @PrimaryKey
    val id: Long,
    val login: String?,
    val avatarUrl: String?,
    val name: String?,
    val location: String?,
    val company: String?,
    val publicRepos: Int,
    val url: String?

) {
    companion object {
        const val TABLE_NAME = "user"
    }
}