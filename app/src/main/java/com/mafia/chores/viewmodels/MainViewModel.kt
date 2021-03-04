package com.mafia.chores.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mafia.chores.MainRepository
import com.mafia.chores.intent.MainIntent
import com.mafia.chores.states.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception


@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository): ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it){
                    is MainIntent.FetchUsers -> fetchUsers()
                }
            }
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Users(repository.getUser())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }


}