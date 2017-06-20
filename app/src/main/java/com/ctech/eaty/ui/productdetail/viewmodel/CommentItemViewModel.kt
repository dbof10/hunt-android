package com.ctech.eaty.ui.productdetail.viewmodel

import android.view.View
import com.ctech.eaty.entity.Comment

data class CommentItemViewModel(private val comment: Comment,private val selected: Boolean = false) : ProductBodyItemViewModel {

    val id: Int get() = comment.id
    val userName: String get() = comment.user.username
    val body: String get() = comment.body
    val imageUrl: String get() = comment.user.imageUrl.smallImgUrl
    val isSelected: Boolean get() = selected
    val replyCountVisibility: Int get() = if(isSelected) View.VISIBLE else View.GONE
    val commentLikeVisibility: Int get() = if(isSelected) View.VISIBLE else View.GONE
    val replyVisibility: Int get() = if(isSelected) View.VISIBLE else View.GONE


}