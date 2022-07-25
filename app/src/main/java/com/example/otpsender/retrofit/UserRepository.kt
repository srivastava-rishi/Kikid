package com.example.otpsender.retrofit

import javax.inject.Inject


class UserRepository
@Inject
constructor(private val api: UserApi) {

    suspend fun getUser() = api.getUserDetails()


}