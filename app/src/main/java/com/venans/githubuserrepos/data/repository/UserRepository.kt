package com.venans.githubuserrepos.data.repository

import androidx.annotation.MainThread
import com.venans.githubuserrepos.data.local.dao.UsersDao
import com.venans.githubuserrepos.data.remote.api.GitHubApiService
import com.venans.githubuserrepos.data.repository.base.NetworkBoundRepository
import com.venans.githubuserrepos.data.repository.base.NetworkOnlyRepository
import com.venans.githubuserrepos.data.repository.base.Resource
import com.venans.githubuserrepos.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response
import javax.inject.Inject

interface UserRepository {
    fun getAllUsers(): Flow<Resource<List<User>>>
    fun getUserById(id: Long): Flow<User>
    fun getUserDetailedInfo(userLogin: String): Flow<Resource<User>>
    fun searchUsers(query: String): Flow<Resource<List<User>>>
}

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
class DefaultUserRepository @Inject constructor(
    private val usersDao: UsersDao,
    private val gitHubApiService: GitHubApiService
) : UserRepository {

    /**
     * Fetched the users from network and stored it in database. Later, data from persistence
     * storage is fetched and emitted.
     */
    override fun getAllUsers(): Flow<Resource<List<User>>> {
        return object : NetworkBoundRepository<List<User>, List<User>>() {

            override suspend fun saveRemoteData(response: List<User>) = usersDao.addUsers(response)

            override fun fetchFromLocal(): Flow<List<User>> = usersDao.getAllUsers()

            override suspend fun fetchFromRemote(): Response<List<User>> {
                var  result =  gitHubApiService.getUsers()
                try {
                    return result.let {
                        val inwrap = it.body() as List<UserResponse>
                        val users = inwrap.map { user ->
                            mapUserDataItem(user)
                        }
                        return@let Response.success(users)
                    } as Response<List<User>>
                } catch (e: Exception) {
                    return  Response.error(result.code(), result.errorBody())
                }
            }
        }.asFlow()
    }

    fun mapUserDataItem(response: UserResponse): User {
        return User(
            response.id,
            response.login,
            response.avatarUrl,
            response.name,
            response.location,
            response.company,
            response.publicReposCount,
            response.url
        )
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

    override fun getUserDetailedInfo(userLogin: String): Flow<Resource<User>> {
        return object : NetworkBoundRepository<User, User>() {

            override suspend fun saveRemoteData(response: User) = usersDao.addUser(response)

            override fun fetchFromLocal(): Flow<User> = usersDao.getUserByLogin(userLogin)

            override suspend fun fetchFromRemote(): Response<User> {
                var  result =  gitHubApiService.getUser(userLogin)
                try {
                    return result.let {
                        var data = mapUserDataItem(it.body()!!)
                        return@let Response.success(data)
                    } as Response<User>
                } catch (e: Exception) {
                    return  Response.error(result.code(), result.errorBody())
                }
            }
        }.asFlow()
    }

    override fun searchUsers(query: String): Flow<Resource<List<User>>> {
        return object : NetworkOnlyRepository<List<User>, List<User>>() {

            override suspend fun fetchFromRemote(): Response<List<User>> {
                var  result =  gitHubApiService.searchUsers(query)
                try {
                    return result.let {
                        val inwrap = it.body()!!.items as List<UserResponse>
                        val users = inwrap.map { user ->
                            mapUserDataItem(user)
                        }
                        return@let Response.success(users)
                    } as Response<List<User>>
                } catch (e: Exception) {
                    return  Response.error(result.code(), result.errorBody())
                }
            }
        }.asFlow()
    }

}
