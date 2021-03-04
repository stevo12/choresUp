package com.mafia.chores

import com.mafia.chores.backend.ApiHelper
import com.mafia.chores.data.User

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun register(user: User) = apiHelper.register(user)

    suspend fun login(user: User) = apiHelper.login(user)

    suspend fun getUser() = apiHelper.getUsers()
}