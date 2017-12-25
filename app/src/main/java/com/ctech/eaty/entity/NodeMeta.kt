package com.ctech.eaty.entity

import android.view.View
import com.google.gson.annotations.SerializedName

data class NodeMeta(
        val width: Int = View.MeasureSpec.UNSPECIFIED,
        val height: Int = View.MeasureSpec.UNSPECIFIED,
        @SerializedName("imageUuid")
        val imageUrl: String? = null,
        @SerializedName("href")
        val url: String? = null,
        @SerializedName("videoId")
        val videoId: String? = null)