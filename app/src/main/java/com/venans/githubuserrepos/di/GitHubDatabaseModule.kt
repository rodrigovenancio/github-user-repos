package  com.venans.githubuserrepos.di

import android.app.Application
import com.venans.githubuserrepos.data.local.GitHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GitHubDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = GitHubDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideUsersDao(database: GitHubDatabase) = database.getUsersDao()

}
