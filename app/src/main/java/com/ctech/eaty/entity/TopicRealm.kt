package com.ctech.eaty.entity

import io.realm.RealmModel
import io.realm.annotations.RealmClass


@RealmClass
open class TopicRealm(var id: Int = -1,
                      var name: String = "",
                      var imageUrl: String = "") : RealmModel {

    fun makeTopic(): Topic {
        return Topic(id, name, imageUrl)
    }

}