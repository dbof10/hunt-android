package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

enum class DisplayNodeType  {

    @SerializedName("paragraph")
    PARAGRAPH,

    @SerializedName("imgix")
    IMAGE,

    @SerializedName("bold")
    BOLD,

    @SerializedName("line")
    LINE,

    @SerializedName("link")
    LINK,

    @SerializedName("video")
    VIDEO;
}