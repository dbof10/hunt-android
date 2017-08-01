package com.ctech.eaty.response

import com.ctech.eaty.entity.Follow
import com.google.gson.annotations.SerializedName

data class FollowerResponse(@SerializedName("followers") val followers: List<Follow>)