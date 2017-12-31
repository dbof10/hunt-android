package com.ctech.eaty.entity

data class NewPostCard(val products: List<Product>) : HomeCard {
    override val type: String
        get() = NEW_POSTS
}