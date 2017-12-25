package com.ctech.eaty.entity

import io.realm.RealmList

data class UpcomingProduct(val id: String, val name: String, val tagline: String, val backgroundUrl: String,
                           val foregroundUrl: String, val subscriberCount: Int = -1, val topSubscribers: List<User> = emptyList()) {
    fun makeRealm(): UpcomingProductRealm {
        val realmList = RealmList<UserRealm>()
        realmList.addAll(topSubscribers.map { it.makeRealm() })
        return UpcomingProductRealm(id, name, tagline, backgroundUrl, foregroundUrl, subscriberCount, realmList)
    }
}
