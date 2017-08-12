package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass


@RealmClass
open class ThumbNail(var id: Int = -1,
                     @SerializedName("media_type") var rawMediaType: String = "",
                     @SerializedName("image_url") var imageUrl: String = "") : RealmModel {


    val mediaType: MediaType? get() = if (rawMediaType.isNullOrEmpty()) null else MediaType.valueOf(rawMediaType)

    companion object {
        val EMPTY = ThumbNail(-1, "", "")
    }
}