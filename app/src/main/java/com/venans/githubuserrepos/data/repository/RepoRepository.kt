package com.venans.githubuserrepos.data.repository

import com.venans.githubuserrepos.data.local.dao.ReposDao
import com.venans.githubuserrepos.data.remote.api.GitHubApiService
import com.venans.githubuserrepos.data.repository.base.NetworkBoundRepository
import com.venans.githubuserrepos.data.repository.base.Resource
import com.venans.githubuserrepos.model.Repo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

interface RepoRepository {
    fun getUserRepos(userLogin: String): Flow<Resource<List<Repo>>>
}

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
class DefaultRepoRepository @Inject constructor(
    private val reposDao: ReposDao,
    private val gitHubApiService: GitHubApiService
) : RepoRepository {

    /**
     * Fetched the users from network and stored it in database. Later, data from persistence
     * storage is fetched and emitted.
     */
    override fun getUserRepos(userLogin: String): Flow<Resource<List<Repo>>> {
        return object : NetworkBoundRepository<List<Repo>, List<Repo>>() {

            override suspend fun saveRemoteData(response: List<Repo>) = reposDao.addRepos(response)

            override fun fetchFromLocal(): Flow<List<Repo>> = reposDao.getAllRepos()

            override suspend fun fetchFromRemote(): Response<List<Repo>> {
                var  result =  gitHubApiService.getUserRepos(userLogin)
                try {
                    return result.let {
                        val inwrap = it.body() as List<RepoResponse>
                        val repos = inwrap.map { repo ->
                            mapRepoDataItem(repo)
                        }
                        return@let Response.success(repos)
                    } as Response<List<Repo>>
                } catch (e: Exception) {
                    return  Response.error(result.code(),result.errorBody())
                }
            }
        }.asFlow()
    }

    fun mapRepoDataItem(response: RepoResponse): Repo {
        return Repo(
            response.id,
            response.name,
            response.fullName,
            response.description,
            response.htmlUrl
        )
    }

}
