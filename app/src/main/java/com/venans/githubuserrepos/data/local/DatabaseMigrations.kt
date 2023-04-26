package com.venans.githubuserrepos.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.venans.githubuserrepos.model.User

object DatabaseMigrations {
    const val DB_VERSION = 2

    val MIGRATIONS: Array<Migration>
        get() = arrayOf<Migration>(
            migration12()
        )
    private fun migration12(): Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${User.TABLE_NAME} ADD COLUMN body TEXT")
        }
    }
}
