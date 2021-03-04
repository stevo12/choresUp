package com.mafia.chores.backend

import com.mafia.chores.data.User

class ApiServiceImp(private val apiService: ApiService): ApiHelper {

    override suspend fun register(user: User): User {
       return apiService.register(user)
    }

    override suspend fun login(user: User): User {
        return apiService.login(user)
    }

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}