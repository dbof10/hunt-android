package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class User(
        val id: Int,
        val name: String,
        val headline: String,
        val username: String,
        @SerializedName("image_url")
        val imageUrl: ImageUrl) {

    companion object {
        val GUEST = User(-1, "", "", "", ImageUrl())
    }

    class ImageUrl(@SerializedName("48px")
                   val smallImgUrl: String = "",
                   @SerializedName("73px")
                   val largeImgUrl: String = "")

}