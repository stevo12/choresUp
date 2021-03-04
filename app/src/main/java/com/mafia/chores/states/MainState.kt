package com.mafia.chores.states

import com.mafia.chores.data.User

sealed class  MainState {

    object Idle: MainState()
    object Loading: MainState()
    data class Users(val users: List<User>): MainState()
    data class Error(val error: String?): MainState()
}