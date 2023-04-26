package  com.venans.githubuserrepos.di

import com.venans.githubuserrepos.data.remote.api.GitHubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GitHubApiModule {
    @Singleton
    @Provides
    fun provideRetrofitService(): GitHubApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()
        .create(GitHubApiService::class.java)
}
