package com.ctech.eaty.ui.home.viewmodel

enum class Type {
    LOADING, ERROR
}

data class FeedFooterItemViewModel(val type: Type)