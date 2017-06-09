package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class ImageUrl(@SerializedName("300px")
                    val smallImgUrl: String,
                    @SerializedName("850px")
                    val largeImgUrl: String)