package com.mafia.chores.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mafia.chores.MainRepository
import com.mafia.chores.data.User
import com.mafia.chores.intent.RegisterLoginUserIntent
import com.mafia.chores.states.RegisterLoginUserState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterLoginUserViewModel(private val user: User, private val repository: MainRepository): ViewModel() {

    val userIntent = Channel<RegisterLoginUserIntent>(Channel.BUFFERED)
    private val _state = MutableStateFlow<RegisterLoginUserState>(RegisterLoginUserState.Idle)
    val state: StateFlow<RegisterLoginUserState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is RegisterLoginUserIntent.CreateUser -> createUser()
                    is RegisterLoginUserIntent.LoginUser -> loginUser()
                }
            }
        }
    }


    private fun createUser() {
        viewModelScope.launch {
            _state.value = RegisterLoginUserState.Idle
            _state.value = try {
                RegisterLoginUserState.CreateUser(repository.register(user))
            } catch (e: Exception) {
                RegisterLoginUserState.Error(e.localizedMessage)
            }
        }
    }

    private fun loginUser() {
        viewModelScope.launch {
            _state.value = RegisterLoginUserState.Idle
            _state.value = try {
                RegisterLoginUserState.LoginUser(repository.login(user))
            } catch (e: Exception) {
                RegisterLoginUserState.Error(e.localizedMessage)
            }
        }
    }
}