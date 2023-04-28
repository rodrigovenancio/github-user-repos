package  com.venans.githubuserrepos.di

import com.venans.githubuserrepos.data.repository.DefaultRepoRepository
import com.venans.githubuserrepos.data.repository.RepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class RepoRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindRepoRepository(repository: DefaultRepoRepository): RepoRepository
}
