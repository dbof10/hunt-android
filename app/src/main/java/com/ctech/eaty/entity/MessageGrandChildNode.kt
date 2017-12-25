package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class MessageGrandChildNode(
        @SerializedName("kind") val type: TextNodeType,
        @SerializedName("data") val meta: NodeMeta?,
        @SerializedName("nodes") val nodes: List<MessageGrandChildNode>? = null,
        @SerializedName("ranges") val ranges: List<TextNodeRange>
)