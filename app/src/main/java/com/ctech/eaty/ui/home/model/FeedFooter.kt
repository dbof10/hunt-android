package com.ctech.eaty.ui.home.model

enum class Type {
    LOADING, ERROR
}

data class FeedFooter(val type: Type): HomeFeed