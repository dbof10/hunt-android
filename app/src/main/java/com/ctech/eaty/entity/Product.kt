package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass


@RealmClass
open class Product(var id: Int = -1, var name: String = "", var tagline: String = "",
                   @SerializedName("comments_count")
                   var commentsCount: Int = -1,
                   @SerializedName("votes_count")
                   var votesCount: Int = -1,
                   @SerializedName("discussion_url")
                   var discussionUrl: String = "",
                   @SerializedName("redirect_url")
                   var redirectUrl: String = "",
                   @SerializedName("screenshot_url")
                   var imageUrl: ImageUrl = ImageUrl(),
                   var user: LiteUser = LiteUser.ANONYMOUS,
                   var thumbnail: ThumbNail = ThumbNail.EMPTY) : RealmModel
