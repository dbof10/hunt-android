package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

open class User(
        val id: Int,
        val name: String,
        val headline: String,
        val username: String,
        @SerializedName("image_url")
        val imageUrl: ImageUrl) : Parcelable {


    companion object {
        val ANONYMOUS = User(-1, "", "", "", ImageUrl())

        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<ImageUrl>(ImageUrl::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(headline)
        dest.writeString(username)
        dest.writeParcelable(imageUrl, 0)
    }
}