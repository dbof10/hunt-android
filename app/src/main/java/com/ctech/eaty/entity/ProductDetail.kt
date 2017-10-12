package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

data class ProductDetail(val id: Int = -1,
                         @SerializedName("comments_count")
                         val commentCount: Int = 0,
                         val name: String = "",
                         val tagline: String = "",
                         val topics: List<Topic> = emptyList(),
                         val makers: List<User> = emptyList(),
                         val thumbnail: ThumbNail = ThumbNail.EMPTY,
                         @SerializedName("redirect_url")
                         val redirectUrl: String = "",
                         @SerializedName("user")
                         val hunter: User = User.ANONYMOUS,
                         @SerializedName("votes_count")
                         val voteCount: Int = 0,
                         val comments: List<Comment> = emptyList(),
                         @SerializedName("related_posts")
                         val relatedPosts: List<Product> = emptyList(),
                         @SerializedName("install_links")
                         val installLinks: List<InstallLink> = emptyList(),
                         @SerializedName("current_user")
                         val currentUser: CurrentUser = CurrentUser(),
                         val media: List<Media> = emptyList(),
                         @SerializedName("created_at")
                         val createdAt: DateTime = DateTime.now()) : RealmModel {

    companion object {
        val EMPTY = ProductDetail()
    }


    fun makeRealm(): ProductDetailRealm {
        val commentList = RealmList<CommentRealm>()
        commentList.addAll(comments.map { it.makeRealm() })

        val topicList = RealmList<Topic>()
        topicList.addAll(topics)

        val makersList = RealmList<UserRealm>()
        makersList.addAll(makers.map { it.makeRealm() })

        val productList = RealmList<ProductRealm>()
        productList.addAll(relatedPosts.map { it.makeRealm() })

        val linksList = RealmList<InstallLinkRealm>()
        linksList.addAll(installLinks.map { it.makeInstallLinkRealm() })

        val mediaList = RealmList<MediaRealm>()
        mediaList.addAll(media.map { it.makeMediaRealm() })
        val fmt = ISODateTimeFormat.dateTime()

        return ProductDetailRealm(id, commentCount, name, tagline, topicList,
                makersList, thumbnail.makeRealm(), redirectUrl, hunter.makeRealm(),
                voteCount,
                commentList,
                productList,
                linksList,
                currentUser,
                mediaList,
                fmt.print(createdAt))
    }
}