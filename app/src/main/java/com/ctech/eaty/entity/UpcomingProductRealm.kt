package com.ctech.eaty.entity

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class UpcomingProductRealm(var id: String = "",
                                var name: String = "",
                                var tagline: String = "",
                                var backgroundUrl: String = "",
                                var foregroundUrl: String = "",
                                var subscriberCount: Int = -1,
                                var topSubscribers: RealmList<UserRealm> = RealmList<UserRealm>()) : RealmModel {

    fun makeProduct(): UpcomingProduct {
        return UpcomingProduct(id, name + "",
                tagline + "", backgroundUrl + "",
                foregroundUrl + "", subscriberCount,
                topSubscribers.map { it.makeUser() })
    }
}