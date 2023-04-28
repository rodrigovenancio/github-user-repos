package com.venans.githubuserrepos.data.repository

import androidx.annotation.MainThread
import com.venans.githubuserrepos.data.local.dao.UsersDao
import com.venans.githubuserrepos.data.remote.api.GitHubApiService
import com.venans.githubuserrepos.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response
import javax.inject.Inject

interface UserRepository {
    fun getAllUsers(): Flow<Resource<List<User>>>
    fun getUserById(id: Long): Flow<User>
}

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
class DefaultUserRepository @Inject constructor(
    private val usersDao: UsersDao,
    private val nyNewsService: GitHubApiService
) : UserRepository {

    /**
     * Fetched the users from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    override fun getAllUsers(): Flow<Resource<List<User>>> {
        return object : NetworkBoundRepository<List<User>, List<User>>() {

            override suspend fun saveRemoteData(response: List<User>) = usersDao.addUsers(response)

            override fun fetchFromLocal(): Flow<List<User>> = usersDao.getAllUsers()

            override suspend fun fetchFromRemote(): Response<List<User>> {
                var  result =  nyNewsService.getUsers()
                try {
                    return result.let {
                        var data = mapUsersDataItem(it)
                        return@let Response.success(data)
                    } as Response<List<User>>
                } catch (e: Exception) {
                    return  Response.error(result.code(),result.errorBody())
                }
            }
        }.asFlow()
    }

    fun mapUsersDataItem(response: Response<List<UsersResponse>>):List<User> {
        val inwrap = response.body() as List<UsersResponse>
        val users = inwrap.map {
            User(
                it.id,
                it.login,
                it.avatarUrl,
                it.name,
                it.location,
                it.company,
                it.publicReposCount,
                it.url
            )
        }

        return users
    }

    /**
     * Retrieves a user with specified [Id].
     * @param Id Unique id of a [User].
     * @return [User] data fetched from the database.
     */
    @MainThread
    override fun getUserById(id: Long): Flow<User> {
        return usersDao.getUserById(id).distinctUntilChanged()
    }
}
