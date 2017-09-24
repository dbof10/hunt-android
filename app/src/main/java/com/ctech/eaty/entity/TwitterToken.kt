package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable

data class TwitterToken(val token: String = "", val tokenSecret: String = "") : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(token)
        writeString(tokenSecret)
    }

    fun isAuthorized(): Boolean{
        return this != UN_AUTH
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TwitterToken> = object : Parcelable.Creator<TwitterToken> {
            override fun createFromParcel(source: Parcel): TwitterToken = TwitterToken(source)
            override fun newArray(size: Int): Array<TwitterToken?> = arrayOfNulls(size)
        }

        val UN_AUTH = TwitterToken("", "")
    }
}