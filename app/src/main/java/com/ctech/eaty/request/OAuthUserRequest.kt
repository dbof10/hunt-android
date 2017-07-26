package com.ctech.eaty.request

import com.ctech.eaty.util.Constants
import com.google.gson.annotations.SerializedName

data class OAuthUserRequest(@SerializedName("client_id") val clientId: String = Constants.CLIENT_ID,
                            @SerializedName("client_secret") val clientSecret: String = Constants.CLIENT_SECRET,
                            @SerializedName("redirect_uri") val redirectUri: String = Constants.REDIRECT_URI,
                            @SerializedName("code") val code: String,
                            @SerializedName("grant_type") val grantType: String = Constants.USER_GRANT_TYPE)