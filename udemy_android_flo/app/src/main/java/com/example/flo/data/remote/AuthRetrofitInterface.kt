package com.example.flo.data.remote

import com.example.flo.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/login")
    fun signup(@Body user: User): Call<AuthResponse>

    @POST("join")
    fun login(@Body uesr: User): Call<AuthResponse>
}