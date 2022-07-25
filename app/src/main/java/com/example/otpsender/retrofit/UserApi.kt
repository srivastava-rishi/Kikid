package com.example.otpsender.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("data.json")
    suspend fun getUserDetails(): Response<List<UserNetworkEntity>>
}