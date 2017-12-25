package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

enum class TextNodeType {
    @SerializedName("state")
    STATE,

    @SerializedName("document")
    DOCUMENT,

    @SerializedName("block")
    BLOCK,

    @SerializedName("text")
    TEXT,

    @SerializedName("range")
    RANGE,

    @SerializedName("mark")
    MARK,

    @SerializedName("inline")
    INLINE;
}