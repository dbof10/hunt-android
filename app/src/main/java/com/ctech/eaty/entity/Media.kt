package com.ctech.eaty.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Media(val id: Int, @SerializedName("media_type") val mediaType: MediaType,
                 @SerializedName("original_width") val width: Int,
                 @SerializedName("original_height") val height: Int,
                 val platform: Platform?,
                 @SerializedName("video_id") val videoId: String?,
                 @SerializedName("image_url") val imageUrl: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Media> = object : Parcelable.Creator<Media> {
            override fun createFromParcel(source: Parcel): Media = Media(source)
            override fun newArray(size: Int): Array<Media?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            MediaType.values()[source.readInt()],
            source.readInt(),
            source.readInt(),
            source.readInt().let {
                if (it == -1) null else Platform.values()[it]
            },
            source.readString(),
            source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeInt(mediaType.ordinal)
        dest.writeInt(width)
        dest.writeInt(height)
        dest.writeInt(platform?.ordinal ?: -1)
        dest.writeString(videoId)
        dest.writeString(imageUrl)
    }
}