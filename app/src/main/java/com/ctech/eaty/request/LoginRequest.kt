package com.ctech.eaty.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("login_provider") val loginProvider: String,
        @SerializedName("oauth_token") val oauthToken: String,
        @SerializedName("oauth_token_secret") val oauthTokenSecret: String) {
    @SerializedName("app")
    val app: String = "ph_mobile"
}