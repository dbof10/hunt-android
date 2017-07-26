package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ImageUrl(@SerializedName("48px")
                    val px48: String = "",
                    @SerializedName("64px")
                    val px64: String = "",
                    @SerializedName("300px")
                    val px300: String = "",
                    @SerializedName("850px")
                    val px850: String = "") : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ImageUrl> = object : Parcelable.Creator<ImageUrl> {
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
}