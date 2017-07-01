package com.ctech.eaty.ui.home.viewmodel;

import com.ctech.eaty.entity.Product
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailItemViewModel

data class ProductItemViewModel(private val product: Product) : HomeItemViewModel, CollectionDetailItemViewModel {
    val id: Int get() = product.id
    val name: String get() = product.name
    val tagline: String get() = product.tagline
    val imageUrl: String get() = product.imageUrl.smallImgUrl
    val votesCount: String get() = product.votesCount.toString()
    val commentsCount: String get() = product.commentsCount.toString()
    val shareUrl: String get() = product.redirectUrl
}
