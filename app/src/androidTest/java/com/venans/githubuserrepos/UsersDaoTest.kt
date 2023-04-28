package com.venans.githubuserrepos

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.venans.githubuserrepos.data.local.GitHubDatabase
import com.venans.githubuserrepos.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersDaoTest {

    private lateinit var mDatabase: GitHubDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_select_users() = runBlocking {
        val users = listOf(
            User(1,
                "torvalds",
                "url://123",
                "Linus Torvalds",
                "EUA",
                "Company X",
                10,
                "https//image1"),
            User(2,
                    "Turing",
                "url://321",
                "Alan Turing",
                "UK",
                "Company Y",
                15,
                "https//image2")
        )

        mDatabase.getUsersDao().addUsers(users)

        val dbUsers = mDatabase.getUsersDao().getAllUsers().first()

        assertThat(dbUsers[0].id, equalTo(users[0].id))
        assertThat(dbUsers[0].name, equalTo(users[0].name))
        assertThat(dbUsers[0].company, equalTo(users[0].company))
        assertThat(dbUsers[0].url, equalTo(users[0].url))

    }

    @Test
    @Throws(InterruptedException::class)
    fun select_user_by_id() = runBlocking {
        val users = listOf(
            User(1,
                "torvalds",
                "url://123",
                "Linus Torvalds",
                "EUA",
                "Company X",
                10,
                "https//image1"),
            User(2,
                "Turing",
                "url://321",
                "Alan Turing",
                "UK",
                "Company Y",
                15,
                "https//image2")
        )

        mDatabase.getUsersDao().addUsers(users)

        var dbUser = mDatabase.getUsersDao().getUserById(1).first()
        assertThat(dbUser.id, equalTo(users[0].id))
        assertThat(dbUser.name, equalTo(users[0].name))
        assertThat(dbUser.company, equalTo(users[0].company))
        assertThat(dbUser.url, equalTo(users[0].url))

        dbUser = mDatabase.getUsersDao().getUserById(2).first()
        assertThat(dbUser.id, equalTo(users[1].id))
        assertThat(dbUser.name, equalTo(users[1].name))
        assertThat(dbUser.company, equalTo(users[1].company))
        assertThat(dbUser.url, equalTo(users[1].url))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}
