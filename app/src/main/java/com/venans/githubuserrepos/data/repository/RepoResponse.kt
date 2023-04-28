package com.venans.githubuserrepos.data.repository

/**
Created by Umer Khawaja on 30,January,2022
Dubai, UAE.
 */
import com.google.gson.annotations.SerializedName

class RepoResponse {

    var id: Long = 0
    var name: String? = null
    @SerializedName("full_name")
    var fullName: String? = null
    var description: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null

}