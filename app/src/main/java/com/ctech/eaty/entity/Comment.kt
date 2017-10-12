package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat


data class Comment(val id: Int, val body: String,
                   @SerializedName("parent_comment_id")
                   val parentCommentId: Int,
                   @SerializedName("child_comments_count")
                   val childCommentCount: Int,
                   @SerializedName("created_at")
                   val createdAt: DateTime,
                   @SerializedName("maker")
                   val isMaker: Boolean,
                   val user: User,
                   @SerializedName("child_comments")
                   val childComments: List<Comment>) {

    fun makeRealm(): CommentRealm {
        val realmList = RealmList<CommentRealm>()
        realmList.addAll(childComments.map { it.makeRealm()})
        return CommentRealm(id, body, parentCommentId, childCommentCount,
                ISODateTimeFormat.dateTime().print(createdAt),
                isMaker, user.makeRealm(), realmList)
    }
}