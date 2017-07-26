package com.ctech.eaty.request

import com.ctech.eaty.util.Constants
import com.google.gson.annotations.SerializedName

data class OAuthClientRequest(@SerializedName("client_id")
                              val clientId: String,
                              @SerializedName("client_secret")
                              val clientSecret: String,
                              @SerializedName("grant_type")
                              val grantType: String) {
    companion object {
        val instance = OAuthClientRequest(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.GRANT_TYPE)
    }
}