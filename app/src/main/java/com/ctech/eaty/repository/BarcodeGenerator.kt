package com.ctech.eaty.repository

import com.ctech.eaty.entity.Comments
import com.ctech.eaty.response.CollectionDetailResponse
import com.ctech.eaty.response.CollectionResponse
import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.response.TopicResponse
import com.ctech.eaty.response.UserResponse
import com.ctech.eaty.response.VoteResponse
import com.ctech.eaty.ui.comment.action.CommentBarCode
import com.ctech.eaty.ui.topicdetail.action.TopicDetailBarCode
import com.ctech.eaty.ui.user.action.UserProductBarCode
import com.ctech.eaty.ui.vote.action.VoteBarCode
import com.ctech.eaty.util.DateUtils
import com.nytimes.android.external.store3.base.impl.BarCode
import org.joda.time.DateTime


object BarcodeGenerator {

    fun createHomeNextBarCode(dayAgo: Int): BarCode = BarCode(ProductResponse::class.java.simpleName,
            DateUtils.getFormattedPastDate(DateTime.now(), dayAgo))


    fun createProductDetailBarCode(id: Int): BarCode = BarCode(ProductDetailResponse::class.java.simpleName, id.toString())

    fun createUserProductBarCode(id: Int, page: Int) = UserProductBarCode(ProductResponse::class.java.simpleName, "User $id page $page", id, page)

    fun createUserBarCode(id: Int): BarCode = BarCode(UserResponse::class.java.simpleName, id.toString())

    fun createCollectionDetailBarCode(id: Int): BarCode = BarCode(CollectionDetailResponse::class.java.simpleName, id.toString())

    fun createCollectionListBarCode(page: Int): BarCode = BarCode(CollectionResponse::class.java.simpleName, page.toString())

    fun createTopicListBarCode(page: Int): BarCode = BarCode(TopicResponse::class.java.simpleName, page.toString())

    fun createTopicDetailBarCode(id: Int, page: Int): TopicDetailBarCode = TopicDetailBarCode(ProductResponse::class.java.simpleName, "Post $id page $page", id, page)

    fun createVoteListBarCode(id: Int, page: Int): VoteBarCode = VoteBarCode(VoteResponse::class.java.simpleName, "Post $id page $page", id, page)

    fun createCommentListBarCode(id: Int, page: Int): CommentBarCode = CommentBarCode(Comments::class.java.simpleName, "Post $id page $page", id, page)

}


