package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class Media(val id: Int, @SerializedName("media_type") val mediaType: MediaType,
                 @SerializedName("original_width") val width: Int,
                 @SerializedName("original_height") val height: Int,
                 @SerializedName("video_id") val videoId: String,
                 @SerializedName("image_url") val imageUrl: String)