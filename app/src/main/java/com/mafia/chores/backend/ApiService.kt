package com.mafia.chores.backend

import com.mafia.chores.data.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/register")
    suspend fun register(@Body user: User): User

    @POST("/login")
    suspend fun login(@Body user: User): User

    @GET("/users")
    suspend fun getUsers(): List<User>


}