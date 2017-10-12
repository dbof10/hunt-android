package com.ctech.eaty.entity

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ProductRealm(var id: Int = -1,
                        var name: String = "",
                        var tagline: String = "",
                        var commentsCount: Int = -1,
                        var votesCount: Int = -1,
                        var discussionUrl: String = "",
                        var redirectUrl: String = "",
                        var imageUrl: ImageUrl = ImageUrl(),
                        var currentUser: CurrentUser = CurrentUser(),
                        var user: UserRealm = UserRealm.ANONYMOUS,
                        var thumbnail: ThumbNailRealm = ThumbNailRealm.EMPTY) : RealmModel {

    fun makeProduct(): Product {
        return Product(id, name + "",
                tagline + "", commentsCount,
                votesCount, discussionUrl + "",
                redirectUrl + "",
                imageUrl.clone(),
                currentUser,
                user.makeUser(),
                thumbnail.makeThumbnail())
    }
}