package com.ctech.eaty.response

import com.ctech.eaty.entity.Like
import com.google.gson.annotations.SerializedName

data class UnlikeResponse(@SerializedName("vote") val vote: Like)