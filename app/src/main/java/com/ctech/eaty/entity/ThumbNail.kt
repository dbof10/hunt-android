package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName


data class ThumbNail(val id: Int, @SerializedName("media_type") val mediaType: MediaType, @SerializedName("image_url") val imageUrl: String)