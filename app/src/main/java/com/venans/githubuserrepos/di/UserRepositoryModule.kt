package  com.venans.githubuserrepos.di

import com.venans.githubuserrepos.data.repository.DefaultUserRepository
import com.venans.githubuserrepos.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Currently UserDetailRepository is only used in ViewModels.
 * UserDetailsViewModel is not injected using @HiltViewModel so can't install in ViewModelComponent.
 */
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class UserRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindUserDetailRepository(repository: DefaultUserRepository): UserRepository
}
