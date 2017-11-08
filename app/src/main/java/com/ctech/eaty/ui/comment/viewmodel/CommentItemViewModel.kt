package com.ctech.eaty.ui.comment.viewmodel

import com.ctech.eaty.entity.Comment
import org.joda.time.DateTime

data class CommentItemViewModel(private val comment: Comment, private val expanded: Boolean = false) {
    val id: Int get() = comment.id
    val parentId: Int get() = comment.parentCommentId
    val name: String get() = comment.user.name
    val headline: String get() = comment.user.headline ?: ""
    val body: String get() = comment.body
    val createdAt: DateTime get() = comment.createdAt
    val imageUrl: String get() = comment.user.imageUrl.px48
    val isExpanded: Boolean get() = expanded
}