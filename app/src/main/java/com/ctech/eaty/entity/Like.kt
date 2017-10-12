package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class Like(@SerializedName("id") val id: Int,
                @SerializedName("user") val user: User)