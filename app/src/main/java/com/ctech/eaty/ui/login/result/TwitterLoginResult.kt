package com.ctech.eaty.ui.login.result

import android.os.Parcel
import android.os.Parcelable
import com.ctech.eaty.entity.TwitterToken

data class TwitterLoginResult(val token: TwitterToken = TwitterToken.UN_AUTH, val error: String = "") : Parcelable {

    constructor(source: Parcel) : this(
            source.readParcelable<TwitterToken>(TwitterToken::class.java.classLoader),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(token, 0)
        writeString(error)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TwitterLoginResult> = object : Parcelable.Creator<TwitterLoginResult> {
            override fun createFromParcel(source: Parcel): TwitterLoginResult = TwitterLoginResult(source)
            override fun newArray(size: Int): Array<TwitterLoginResult?> = arrayOfNulls(size)
        }
    }
}