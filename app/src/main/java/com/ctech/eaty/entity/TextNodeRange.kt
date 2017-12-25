package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class TextNodeRange(
        @SerializedName("kind") val type: TextNodeType,
        @SerializedName("text") val text: String,
        @SerializedName("marks") val marks: List<DocAttributeNode>)