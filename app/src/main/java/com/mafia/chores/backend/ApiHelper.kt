package com.mafia.chores.backend

import com.mafia.chores.data.User

interface ApiHelper {

    suspend fun register(user: User): User

    suspend fun login(user: User): User

    suspend fun getUsers(): List<User>
}