package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName


data class Collection(
        val id: Int,
        val name: String,
        val title: String,
        @SerializedName("background_image_url")
        val backgroundImageUrl: String,
        @SerializedName("collection_url")
        val collectionUrl: String,
        val posts: List<Product>)
