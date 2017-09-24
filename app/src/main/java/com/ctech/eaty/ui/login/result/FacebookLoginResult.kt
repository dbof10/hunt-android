package com.ctech.eaty.ui.login.result

import android.os.Parcel
import android.os.Parcelable

data class FacebookLoginResult(val token: String = "", val error: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeString(error)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FacebookLoginResult> {
        override fun createFromParcel(parcel: Parcel): FacebookLoginResult {
            return FacebookLoginResult(parcel)
        }

        override fun newArray(size: Int): Array<FacebookLoginResult?> {
            return arrayOfNulls(size)
        }
    }
}