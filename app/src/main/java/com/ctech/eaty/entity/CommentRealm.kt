package com.ctech.eaty.entity

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import org.joda.time.format.ISODateTimeFormat

@RealmClass
open class CommentRealm(var id: Int = -1,
                        var body: String = "",
                        var parentCommentId: Int = -1,
                        var childCommentCount: Int = 0,
                        var createdAt: String = "",
                        var isMaker: Boolean = false,
                        var user: UserRealm = UserRealm.ANONYMOUS,
                        var childComments: RealmList<CommentRealm> = RealmList()) : RealmModel {

    fun makeComment(): Comment {
        val fmt = ISODateTimeFormat.dateTimeParser()
        return Comment(id, body, parentCommentId, childCommentCount,
                fmt.parseDateTime(createdAt), isMaker, user.makeUser(),
                childComments.map { it.makeComment() })
    }
}