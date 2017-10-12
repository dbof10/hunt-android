package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class Vote(@SerializedName("id") val id: Int,
                @SerializedName("user") val user: User,
                @SerializedName("created_at") val createAt: DateTime)