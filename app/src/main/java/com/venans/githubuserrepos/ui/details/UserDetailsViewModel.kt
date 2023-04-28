package com.venans.githubuserrepos.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.venans.githubuserrepos.data.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * ViewModel for [UserDetailsActivity]
 */
@ExperimentalCoroutinesApi
class UserDetailsViewModel @AssistedInject constructor(
    userRepository: UserRepository,
    @Assisted userId: Long
) : ViewModel() {

    val user = userRepository.getUserById(userId).asLiveData()

    @AssistedFactory
    interface UserDetailsViewModelFactory {
        fun create(userId: Long): UserDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: UserDetailsViewModelFactory,
            userId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId) as T
            }
        }
    }
}
