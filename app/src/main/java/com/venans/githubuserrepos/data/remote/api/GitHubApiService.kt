

package com.venans.githubuserrepos.data.remote.api

import com.venans.githubuserrepos.data.repository.RepoResponse
import com.venans.githubuserrepos.data.repository.UserResponse
import com.venans.githubuserrepos.data.repository.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Service to fetch GitHub data using.
 */
interface GitHubApiService {

    @GET("/users")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("/users/{user_login}")
    suspend fun getUser(@Path("user_login") userLogin: String): Response<UserResponse>

    @GET("/users/{user_login}/repos")
    suspend fun getUserRepos(@Path("user_login") userLogin: String): Response<List<RepoResponse>>

    @GET("/search/users")
    suspend fun searchUsers(@Query("q") query: String): Response<UserSearchResponse>


}
