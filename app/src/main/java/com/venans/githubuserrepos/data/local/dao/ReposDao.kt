package  com.venans.githubuserrepos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.venans.githubuserrepos.model.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO)
 */
@Dao
interface ReposDao {

    /**
     * Inserts [repos] into the [Repo.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param repos Repos
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepos(repos: List<Repo>)

    /**
     * Deletes all the repos from the [Repo.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${Repo.TABLE_NAME}")
    suspend fun deleteAllRepos()

    /**
     * Fetches all the repos from the [Repo.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Repo.TABLE_NAME}")
    fun getAllRepos(): Flow<List<Repo>>
}
