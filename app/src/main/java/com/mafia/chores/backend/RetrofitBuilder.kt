package com.mafia.chores.backend

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {

    private fun setLogger(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    const val BASE_URL = "http://192.168.1.100:8080/"


    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(setLogger())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiService : ApiService = getRetrofit().create(ApiService::class.java)
}