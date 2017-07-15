package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

enum class MediaType(val type: String) {
    @SerializedName("video") VIDEO("video"),
    @SerializedName("image") IMAGE("image");

    override fun toString() = type
}