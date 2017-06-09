package com.ctech.eaty.entity

import com.ctech.eaty.util.Constants
import com.google.gson.annotations.SerializedName

data class Authentication(@SerializedName("client_id")
                          val clientId: String,
                          @SerializedName("client_secret")
                          val clientSecret: String,
                          @SerializedName("grant_type")
                          val grantType: String) {
    companion object {
        val instance = Authentication(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.GRANT_TYPE)
    }
}