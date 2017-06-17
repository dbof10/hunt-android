package com.ctech.eaty.entity

enum class MediaType(val type: String) {
    VIDEO("video"), Image("image");

    override fun toString() = type
}