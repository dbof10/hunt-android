package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class Topic(var id: Int = -1,
                 var name: String = "",
                 @SerializedName("image")
                 var imageUrl: String = "") : RealmModel, Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Topic> = object : Parcelable.Creator<Topic> {
            override fun createFromParcel(source: Parcel): Topic = Topic(source)
            override fun newArray(size: Int): Array<Topic?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(imageUrl)
    }
}