package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class MessagePayload(
        @SerializedName("kind") val type: TextNodeType,
        @SerializedName("nodes") val nodes: List<MessageChildNode>
)