package com.venans.githubuserrepos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.venans.githubuserrepos.data.local.dao.UsersDao
import com.venans.githubuserrepos.model.User

/**
 * GitHub User database.
 * It provides DAO [UsersDao] by using method [getUsersDao]
 */
@Database(
    entities = [User::class],
    version = DatabaseMigrations.DB_VERSION
)
abstract class GitHubDatabase : RoomDatabase() {

    /**
     * @return [UsersDao] NY Articles Data Access Object.
     */
    abstract fun getUsersDao(): UsersDao

    companion object {
        const val DB_NAME = "github_users_database"

        @Volatile
        private var INSTANCE: GitHubDatabase? = null

        fun getInstance(context: Context): GitHubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GitHubDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
