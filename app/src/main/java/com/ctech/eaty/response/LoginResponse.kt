package com.ctech.eaty.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("access_token") val accessToken: String,
                         @SerializedName("type") val type: String,
                         @SerializedName("first_time_user") val firstTime: Boolean)