package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class Topic(val id: Int, val name: String, @SerializedName("image") val imageUrl: String)