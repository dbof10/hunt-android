package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class Notification(val body: String, val id: Int, val seen: Boolean, val sentence: String, val type: String,
                        @SerializedName("from_user") val user: User,
                        @SerializedName("created_at") val createdAt: DateTime)