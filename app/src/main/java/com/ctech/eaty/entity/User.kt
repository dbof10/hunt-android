package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

open class User(
        val id: Int = -1,
        val name: String = "",
        val headline: String? = "",
        val username: String = "",
        @SerializedName("image_url")
        val imageUrl: ImageUrl = ImageUrl()) : Parcelable {


    companion object {
        val ANONYMOUS = User()

        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (headline != other.headline) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + (headline?.hashCode() ?: 0)
        result = 31 * result + username.hashCode()
        return result
    }


    fun makeRealm(): UserRealm {
        return UserRealm(id, name, username, headline, imageUrl)
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', headline=$headline, username='$username', imageUrl=$imageUrl)"
    }


}