package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName


data class Comment(val id: Int, val body: String,
                   @SerializedName("parent_comment_id")
                   val parentComment: Int,
                   @SerializedName("child_comments_count")
                   val childCommentCount: Int,
                   @SerializedName("maker")
                   val isMaker: Boolean,
                   val user: User,
                   val level: Int,
                   @SerializedName("child_comments")
                   val childComments: List<Comment>)