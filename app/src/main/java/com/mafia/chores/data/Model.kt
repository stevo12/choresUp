package com.mafia.chores.data

import com.squareup.moshi.Json

data class User(
    @Json(name = "username") var username: String="",
    @Json(name = "password") var password: String="",
    @Json(name= "id") var id: String=""
)

data class BaseResponse(
    @Json(name = "toke") val token: String,
    @Json(name = "username") val username: String,
    @Json(name = "expiresIn") val expiresIn: Int,
    @Json(name = "id") val id: String
)