package com.ctech.eaty.entity

data class TopicCard(val products: List<Product>) : HomeCard {
    override val type: String
        get() = SUGGESTED_PRODUCTS
}