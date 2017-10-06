package com.ctech.eaty.ui.home.viewmodel;

import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.User
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailItemViewModel

data class ProductItemViewModel(private val product: Product) : HomeItemViewModel, CollectionDetailItemViewModel {
    val id: Int get() = product.id
    val name: String get() = product.name
    val tagline: String get() = product.tagline
    val imageUrl: String get() = product.imageUrl.px300
    val votesCount: String get() = product.votesCount.toString()
    val commentsCount: String get() = product.commentsCount.toString()
    val discussUrl: String get() = product.discussionUrl
    val thumbnailUrl: String get() = product.thumbnail.imageUrl
    val userName get() = product.user.name
    val userImageUrl get() = product.user.imageUrl.px48
    val user: User
        get() =
            User(product.user.id, product.user.name, product.user.headline, product.user.username, product.user.imageUrl)

}
