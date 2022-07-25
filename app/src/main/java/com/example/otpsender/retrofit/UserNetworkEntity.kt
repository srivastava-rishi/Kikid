package com.example.otpsender.retrofit

import java.io.Serializable


data class UserNetworkEntity(
    var id: String,
    var name: String,
    var title: String,
    var phonenumber: String,
    var image: String,
    var eligible: Boolean
): Serializable
