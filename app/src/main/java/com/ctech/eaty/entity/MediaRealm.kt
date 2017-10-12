package com.ctech.eaty.entity

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class MediaRealm(var id: Int = -1,
                      var mediaType: String = MediaType.IMAGE.name,
                      var width: Int = 0,
                      var height: Int = 0,
                      var platform: String? = null,
                      var videoId: String? = null,
                      var imageUrl: String = "") : RealmModel {

    fun makeMedia(): Media {
        return Media(id, MediaType.valueOf(mediaType), width, height,
                if (!platform.isNullOrEmpty())
                    Platform.valueOf(platform!!)
                else
                    null,
                videoId, imageUrl)
    }
}