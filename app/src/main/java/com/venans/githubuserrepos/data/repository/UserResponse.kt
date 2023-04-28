package com.venans.githubuserrepos.data.repository

/**
Created by Umer Khawaja on 30,January,2022
Dubai, UAE.
 */
import com.google.gson.annotations.SerializedName

class UserResponse {

    var login: String? = null
    var id: Long = 0
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
    var name: String? = null
    var location: String? = null
    var company: String? = null
    @SerializedName("public_repos")
    var publicReposCount: Int = 0
    var url: String? = null

}