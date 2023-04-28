package com.venans.githubuserrepos.data.repository.base

import androidx.annotation.MainThread
import kotlinx.coroutines.flow.*
import retrofit2.Response

/**
 * A repository which provides resource for remote only end points.
 *
 * [REQUEST] represents the type for network.
 */
abstract class NetworkOnlyRepository<RESULT, REQUEST> {

    fun asFlow() = flow<Resource<REQUEST>> {

        // Fetch info from remote api
        val apiResponse = fetchFromRemote()

        // Parse body
        val body = apiResponse.body()

        // Check for response validation
        if (apiResponse.isSuccessful && body != null) {
            emit(Resource.Success(body))
        } else {
            // Emit error state if something went wrong
            emit(Resource.Failed(apiResponse.message()))
        }

    }.catch { e ->
        e.printStackTrace()
        emit(Resource.Failed("Network error."))
    }

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}
