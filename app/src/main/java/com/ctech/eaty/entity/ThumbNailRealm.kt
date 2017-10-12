package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ThumbNailRealm(var id: Int = -1,
                          @SerializedName("media_type")
                          var rawMediaType: String = "",
                          @SerializedName("image_url")
                          var imageUrl: String = "") : RealmModel {


    val mediaType: MediaType? get() = if (rawMediaType.isEmpty()) null else MediaType.valueOf(rawMediaType)

    companion object {
        val EMPTY = ThumbNailRealm(-1, "", "")
    }

    fun makeThumbnail() =
            ThumbNail(id, "", rawMediaType + "", imageUrl + "")
}