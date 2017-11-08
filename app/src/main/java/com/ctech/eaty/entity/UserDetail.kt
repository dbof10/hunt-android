package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserDetail(
        id: Int = 0,
        name: String = "",
        headline: String? = "",
        username: String = "",
        var email: String = "",
        @SerializedName("website_url")
        var webUrl: String? = "",
        imageUrl: ImageUrl = ImageUrl(),
        @SerializedName("notifications")
        var notification: NotificationIndicator = NotificationIndicator(),
        @SerializedName("followers_count")
        var followerCount: Int = 0,
        @SerializedName("followings_count")
        var followingCount: Int = 0,
        @SerializedName("posts_count")
        var productCount: Int = 0) : User(id, name, headline, username, imageUrl), Parcelable {

    companion object {
        val GUEST = UserDetail()
        @JvmField
        val CREATOR: Parcelable.Creator<UserDetail> = object : Parcelable.Creator<UserDetail> {
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
            source.readString(),
            source.readParcelable<ImageUrl>(ImageUrl::class.java.classLoader),
            source.readParcelable<NotificationIndicator>(NotificationIndicator::class.java.classLoader),
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(headline)
        dest.writeString(username)
        dest.writeString(email)
        dest.writeString(webUrl)
        dest.writeParcelable(imageUrl, 0)
        dest.writeParcelable(notification, 0)
        dest.writeInt(followerCount)
        dest.writeInt(followingCount)
        dest.writeInt(productCount)
    }

    override fun toString(): String {
        return "UserDetail(email='$email', webUrl=$webUrl, notification=$notification, followerCount=$followerCount, followingCount=$followingCount, productCount=$productCount)"
    }


}