package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserDetail(
        id: Int,
        name: String,
        headline: String,
        username: String,
        @SerializedName("website_url")
        val webUrl: String?,
        imageUrl: ImageUrl,
        @SerializedName("notifications")
        val notification: NotificationIndicator,
        @SerializedName("followers_count")
        val followerCount: Int,
        @SerializedName("followings_count")
        val followingCount: Int,
        @SerializedName("posts")
        val products: List<Product>) : User(id, name, headline, username, imageUrl), Parcelable {

    companion object {
        val GUEST = UserDetail(-1, "", "", "", "",
                ImageUrl(), NotificationIndicator(), 0, 0, emptyList())
        @JvmField val CREATOR: Parcelable.Creator<UserDetail> = object : Parcelable.Creator<UserDetail> {
            override fun createFromParcel(source: Parcel): UserDetail = UserDetail(source)
            override fun newArray(size: Int): Array<UserDetail?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<ImageUrl>(ImageUrl::class.java.classLoader),
            source.readParcelable<NotificationIndicator>(NotificationIndicator::class.java.classLoader),
            source.readInt(),
            source.readInt(),
            ArrayList<Product>().apply { source.readList(this, Product::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(headline)
        dest.writeString(username)
        dest.writeString(webUrl)
        dest.writeParcelable(imageUrl, 0)
        dest.writeParcelable(notification, 0)
        dest.writeInt(followerCount)
        dest.writeInt(followingCount)
        dest.writeList(products)
    }
}