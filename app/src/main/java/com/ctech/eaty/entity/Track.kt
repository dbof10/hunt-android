package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class Track(val id: Int, val title: String, val duration: Int, val description: String,
                 @SerializedName("stream_url") val streamUrl: String,
                 @SerializedName("download_url") val downloadUrl: String,
                 @SerializedName("artwork_url") val imageUrl: String,
                 val streamable: Boolean, val downloadable: Boolean)