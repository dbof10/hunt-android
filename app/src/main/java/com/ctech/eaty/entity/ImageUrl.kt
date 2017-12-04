package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ImageUrl(@SerializedName("48px")
                    var px48: String = "",
                    @SerializedName("64px")
                    var px64: String = "",
                    @SerializedName("120px")
                    var px120: String = "",
                    @SerializedName("300px")
                    var px300: String = "",
                    @SerializedName("850px")
                    var px850: String = "") : Parcelable, RealmModel, Cloneable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImageUrl> = object : Parcelable.Creator<ImageUrl> {
            override fun createFromParcel(source: Parcel): ImageUrl = ImageUrl(source)
            override fun newArray(size: Int): Array<ImageUrl?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(px48)
        dest.writeString(px64)
        dest.writeString(px300)
        dest.writeString(px850)
    }

    public override fun clone(): ImageUrl {
        return ImageUrl(px48 + "", px64 + "", px300 + "", px850 + "")
    }

    override fun toString(): String {
        return "ImageUrl(px48='$px48', px64='$px64', px300='$px300', px850='$px850')"
    }


}