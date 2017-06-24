package com.ctech.eaty.ui.home.viewmodel;

import com.ctech.eaty.entity.Product

data class ProductItemViewModel(private val product: Product) : HomeItemViewModel {
    val id: Int get() = product.id
    val name: String get() = product.name
    val tagline: String get() = product.tagline
    val imageUrl: String get() = product.imageUrl.smallImgUrl
    val votesCount: String get() = product.votesCount.toString()
    val commentsCount: String get() = product.commentsCount.toString()
}
