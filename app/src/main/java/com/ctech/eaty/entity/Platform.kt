package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

enum class Platform(val type: String) {
    @SerializedName("ios") IOS("ios"),
    @SerializedName("android") ANDROID("android"),
    @SerializedName("youtube") YOUTUBE("youtube");

    override fun toString() = type
}