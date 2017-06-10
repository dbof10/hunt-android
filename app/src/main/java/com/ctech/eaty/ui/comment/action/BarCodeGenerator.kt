package com.ctech.eaty.ui.comment.action

import com.ctech.eaty.entity.Comments

class BarCodeGenerator {

    fun currentBarCode(id: Int): CommentBarCode = CommentBarCode(Comments::class.java.simpleName, "Post $id", id)

    fun generateNextBarCode(id: Int, page: Int): CommentBarCode = CommentBarCode(Comments::class.java.simpleName, "Post $id page $page", id, page)

}