package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class Vote(val id: Int, val user: User, @SerializedName("created_at") val createAt: DateTime)