package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass


data class ThumbNail(val id: Int = -1,
                     @SerializedName("image_uuid")
                     val imageUUID: String = "",
                     @SerializedName("media_type")
                     val rawMediaType: String = "",
                     @SerializedName("image_url")
                     val imageUrl: String = ""){


    val mediaType: MediaType? get() = if (rawMediaType.isEmpty()) null else MediaType.valueOf(rawMediaType)

    companion object {
        val EMPTY = ThumbNail(-1, "", "")
    }

    fun makeRealm() = ThumbNailRealm(id,rawMediaType + "", imageUrl + "")
}