package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable

data class NotificationIndicator(val total: Int = 0, val unseen: Int = 0) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<NotificationIndicator> = object : Parcelable.Creator<NotificationIndicator> {
            override fun createFromParcel(source: Parcel): NotificationIndicator = NotificationIndicator(source)
            override fun newArray(size: Int): Array<NotificationIndicator?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(total)
        dest.writeInt(unseen)
    }
}
