package com.venans.githubuserrepos.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.venans.githubuserrepos.data.repository.UserRepository
import com.venans.githubuserrepos.model.State
import com.venans.githubuserrepos.model.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel for [UserDetailsActivity]
 */
class UserDetailsViewModel @AssistedInject constructor(
    private val userRepository: UserRepository,
    @Assisted userId: Long,
    @Assisted userLogin: String
) : ViewModel() {

    val user = userRepository.getUserById(userId).asLiveData()

    private val _userDetailedInfo: MutableStateFlow<State<User>> = MutableStateFlow(State.loading())

    val userDetailedInfo: StateFlow<State<User>> = _userDetailedInfo

    fun getUser(userLogin: String) {
        viewModelScope.launch {
            userRepository.getUserDetailedInfo(userLogin)
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _userDetailedInfo.value = state }
        }
    }

    @AssistedFactory
    interface UserDetailsViewModelFactory {
        fun create(userId: Long, userLogin: String): UserDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: UserDetailsViewModelFactory,
            userId: Long,
            userLogin: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId, userLogin) as T
            }
        }
    }
}
