package com.ctech.eaty.ui.home.viewmodel

import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.User
import com.ctech.eaty.ui.collectiondetail.viewmodel.CollectionDetailItemViewModel
import com.ctech.eaty.util.Constants

data class ProductItemViewModel(private val product: Product) : CollectionDetailItemViewModel {
    val id: Int get() = product.id
    val name get() = product.name
    val tagline get() = product.tagline
    val imageUrl: String
        get() {
            return if (product.imageUrl.px300.isEmpty()) {
                "${Constants.PRODUCT_HUNT_POST_CDN}/${product.thumbnail.imageUUID}"
            } else {
                product.thumbnail.imageUrl
            }
        }

    val votesCountDisplay get() = product.votesCount.toString()
    val votesCount get() = product.votesCount

    val commentsCountDisplay get() = product.commentsCount.toString()
    val commentsCount get() = product.commentsCount

    val discussUrl get() = product.discussionUrl
    val thumbnailUrl get() = product.thumbnail.imageUrl
    val userName get() = product.user.name
    val userImageUrl get() = product.user.imageUrl.px48
    val user
        get() =
            User(product.user.id, product.user.name, product.user.headline, product.user.username, product.user.imageUrl)
    val liked get() = product.currentUser.liked
}
