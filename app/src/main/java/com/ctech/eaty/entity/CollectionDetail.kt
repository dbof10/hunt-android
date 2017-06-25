package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class CollectionDetail(val id: Int,
                            val name: String,
                            val title: String,
                            @SerializedName("background_image_url")
                            val backgroundImageUrl: String,
                            @SerializedName("collection_url")
                            val collectionUrl: String,
                            @SerializedName("posts_count")
                            val postsCount: Int,
                            val user: User,
                            @SerializedName("posts")
                            val products: List<Product>) {


    companion object {
        val EMPTY = CollectionDetail(-1, "", "", "", "", 0, User.GUEST, emptyList())
    }
}