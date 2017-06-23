package com.ctech.eaty.ui.vote.action

import com.ctech.eaty.response.VoteResponse

class BarCodeGenerator {

    fun currentBarCode(id: Int): VoteBarCode = VoteBarCode(VoteResponse::class.java.simpleName, "Post $id", id)

    fun generateNextBarCode(id: Int, page: Int): VoteBarCode = VoteBarCode(VoteResponse::class.java.simpleName, "Post $id page $page", id, page)

}