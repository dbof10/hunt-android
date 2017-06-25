package com.ctech.eaty.ui.collectiondetail.viewmodel

import com.ctech.eaty.entity.CollectionDetail

data class CollectionHeaderItemViewModel(private val collection: CollectionDetail) : CollectionDetailItemViewModel {

    val id: Int get() = collection.id
    val userName: String get() = collection.user.name
    val headLine: String get() = collection.user.headline
    val imageUrl: String get() = collection.user.imageUrl.smallImgUrl
    val description: String get() = collection.title
}