package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName


data class Product(val id: Int, val name: String, val tagline: String,
                   @SerializedName("comments_count")
                   val commentsCount: Int,
                   @SerializedName("votes_count")
                   val votesCount: Int,
                   @SerializedName("discussion_url")
                   val discussionUrl: String,
                   @SerializedName("redirect_url")
                   val redirectUrl: String,
                   @SerializedName("screenshot_url")
                   val imageUrl: ImageUrl,
                   val thumbnail: ThumbNail)