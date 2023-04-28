package com.venans.githubuserrepos.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venans.githubuserrepos.data.repository.UserRepository
import com.venans.githubuserrepos.model.State
import com.venans.githubuserrepos.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [MainActivity]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _users: MutableStateFlow<State<List<User>>> = MutableStateFlow(State.loading())

    val users: StateFlow<State<List<User>>> = _users

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers()
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _users.value = state }
        }
    }
}
