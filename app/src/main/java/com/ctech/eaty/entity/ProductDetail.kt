package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class ProductDetail(val id: Int, @SerializedName("comments_count") val commentCount: Int, val name: String,
                         val tagline: String, val topics: List<Topic>, val makers: List<User>,
                         val thumbnail: ThumbNail,
                         @SerializedName("redirect_url") val redirectUrl: String,
                         @SerializedName("user")
                         val hunter: User,
                         @SerializedName("votes_count")
                         val voteCount: Int,
                         val comments: List<Comment>,
                         @SerializedName("related_posts") val relatedPosts: List<Product>,
                         @SerializedName("install_links") val installLinks: List<InstallLink>,
                         val media: List<Media>,
                         @SerializedName("created_at") val createdAt: DateTime) {

    companion object {
        val EMPTY = ProductDetail(-1, 0, "", "", emptyList(), emptyList(),
                ThumbNail.EMPTY, "", User.ANONYMOUS, 0, emptyList(),
                emptyList(), emptyList(), emptyList(), DateTime.now())
    }

    data class InstallLink(@SerializedName("redirect_url") val url: String,
                           val platform: Platform?)
}