package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class DocAttributeNode(@SerializedName("data")
                            val meta: NodeMeta,
                            @SerializedName("kind")
                            val type: TextNodeType,
                            @SerializedName("type")
                            val value: DisplayNodeType)