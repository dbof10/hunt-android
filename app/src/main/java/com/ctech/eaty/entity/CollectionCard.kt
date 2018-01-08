package com.ctech.eaty.entity

data class CollectionCard(val id: String,
                          val name: String,
                          val title: String,
                          val imageUrl: String,
                          val products: List<Product>) : HomeCard {
    override val type: String
        get() = COLLECTION
}