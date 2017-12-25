package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class MessageChildNode(
        @SerializedName("kind") val type: TextNodeType,
        @SerializedName("data") val meta: NodeMeta,
        @SerializedName("type") val docType: DisplayNodeType,
        @SerializedName("nodes") val nodes: List<MessageGrandChildNode>)