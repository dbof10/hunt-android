package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName


data class Product(val id: Int = -1,
                   val name: String = "",
                   val tagline: String = "",
                   @SerializedName("comments_count")
                   val commentsCount: Int = -1,
                   @SerializedName("votes_count")
                   val votesCount: Int = -1,
                   @SerializedName("discussion_url")
                   val discussionUrl: String = "",
                   @SerializedName("redirect_url")
                   val redirectUrl: String = "",
                   @SerializedName("screenshot_url")
                   val imageUrl: ImageUrl = ImageUrl(),
                   @SerializedName("current_user")
                   val currentUser: CurrentUser = CurrentUser(),
                   val user: User = User.ANONYMOUS,
                   val thumbnail: ThumbNail = ThumbNail.EMPTY) {

    fun makeRealm(): ProductRealm {
        return ProductRealm(id, name,
                tagline, commentsCount,
                votesCount, discussionUrl,
                redirectUrl, imageUrl,
                currentUser,
                user.makeRealm(),
                thumbnail.makeRealm())
    }
}
