package com.ctech.eaty.entity

enum class Platform(val type: String) {
    IOS("ios"), ANDROID("android"), YOUTUBE("youtube");

    override fun toString() = type
}