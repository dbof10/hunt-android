package com.ctech.eaty.response

import com.google.gson.annotations.SerializedName

data class RelationshipResponse(@SerializedName("following_user_ids") val followingIds: List<Int>)