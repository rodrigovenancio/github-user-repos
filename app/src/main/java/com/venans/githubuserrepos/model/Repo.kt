package com.venans.githubuserrepos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.venans.githubuserrepos.model.Repo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Repo (

    @PrimaryKey
    val id: Long,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val htmlUrl: String?

) {
    companion object {
        const val TABLE_NAME = "repo"
    }
}