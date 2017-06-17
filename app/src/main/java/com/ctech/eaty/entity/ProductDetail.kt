package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class ProductDetail(val id: Int, @SerializedName("comments_count") val commentCount: Int, val name: String,
                         val tagline: String, val topics: List<Topic>, val makers: List<User>,
                         val thumbnail: ThumbNail, @SerializedName("user") val hunter: User,
                         @SerializedName("votes_count") val voteCount: Int,
                         val comments: List<Comment>, @SerializedName("related_posts") val relatedPosts: List<Product>,
                         @SerializedName("install_links") val installLinks: List<InstallLink>,
                         val media: List<Media>) {

    data class InstallLink(@SerializedName("redirect_url") val url: String,
                           val platform: Platform)
}