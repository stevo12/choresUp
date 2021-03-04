package com.mafia.chores.states

import com.mafia.chores.data.User

sealed class RegisterLoginUserState {

    object Idle: RegisterLoginUserState()
    data class CreateUser(val user: User): RegisterLoginUserState()
    data class LoginUser(val user: User): RegisterLoginUserState()
    data class Error(val error: String?): RegisterLoginUserState()
}