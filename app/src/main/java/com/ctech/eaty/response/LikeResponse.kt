package com.ctech.eaty.response

import com.ctech.eaty.entity.Like
import com.google.gson.annotations.SerializedName

data class LikeResponse(@SerializedName("vote") val vote: Like)