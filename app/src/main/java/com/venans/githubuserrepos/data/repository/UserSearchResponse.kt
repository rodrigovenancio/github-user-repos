package com.venans.githubuserrepos.data.repository

import com.google.gson.annotations.SerializedName

class UserSearchResponse {

    @SerializedName("total_count")
    var totalCount:Int = 0

    var items: List<UserResponse>? = null

}