

package com.venans.githubuserrepos.data.remote.api

import com.venans.githubuserrepos.data.repository.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Service to fetch GitHub data using.
 */
interface GitHubApiService {

    @GET("/users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("/users/{user_login}")
    suspend fun getUser(@Path("user_login") userLogin: String): Response<UserResponse>

}
