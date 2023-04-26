

package com.venans.githubuserrepos.data.remote.api

import com.venans.githubuserrepos.data.repository.UsersResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Service to fetch GitHub data using.
 */
interface GitHubApiService {

    @GET("/users")
    suspend fun getUsers(): Response<UsersResponse>

}
