package  com.venans.githubuserrepos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.venans.githubuserrepos.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO)
 */
@Dao
interface UsersDao {

    /**
     * Inserts [users] into the [User.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param users Users
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<User>)

    /**
     * Inserts [user] into the [User.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param user User
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    /**
     * Deletes all the users from the [User.TABLE_NAME] table.
     */
    @Query("DELETE FROM ${User.TABLE_NAME}")
    suspend fun deleteAllUsers()

    /**
     * Fetches the user from the [User.TABLE_NAME] table whose id is [id].
     * @param id Unique ID of [User]
     * @return [Flow] of [User] from database table.
     */
    @Query("SELECT * FROM ${User.TABLE_NAME} WHERE ID = :id")
    fun getUserById(id: Long): Flow<User>

    /**
     * Fetches the user from the [User.TABLE_NAME] table whose id is [id].
     * @param id Unique ID of [User]
     * @return [Flow] of [User] from database table.
     */
    @Query("SELECT * FROM ${User.TABLE_NAME} WHERE LOGIN = :login")
    fun getUserByLogin(login: String): Flow<User>

    /**
     * Fetches all the users from the [User.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("SELECT * FROM ${User.TABLE_NAME}")
    fun getAllUsers(): Flow<List<User>>
}
