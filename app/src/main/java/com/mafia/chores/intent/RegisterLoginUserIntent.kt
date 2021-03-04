package com.mafia.chores.intent

sealed class RegisterLoginUserIntent {

    object CreateUser: RegisterLoginUserIntent()
    object LoginUser: RegisterLoginUserIntent()
}
