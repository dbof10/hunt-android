package com.ctech.eaty.response

import com.ctech.eaty.entity.Track
import com.google.gson.annotations.SerializedName

data class RadioResponse(val tracks: List<Track>, @SerializedName("artwork_url") val imageUrl: String) {
    companion object {
        val EMPTY = RadioResponse(emptyList(), "")
    }
}