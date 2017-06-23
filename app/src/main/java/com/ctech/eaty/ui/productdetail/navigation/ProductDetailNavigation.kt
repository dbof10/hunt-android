package com.ctech.eaty.ui.productdetail.navigation

import android.app.ActivityOptions
import android.content.Intent
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.vote.view.VoteActivity
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class ProductDetailNavigation @Inject constructor(private val context: ProductDetailActivity) {

    fun toVote(postId: Int, voteCount: Int): Completable = Completable.fromAction {
        val intent = VoteActivity.newIntent(context, postId, voteCount)
        context.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(context).toBundle())
    }

    fun toComment(id: Int): Completable {
        return Completable.fromAction {
            val intent = CommentActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    fun toShare(link: String): Completable {
        return Completable.fromAction {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            context.startActivity(Intent.createChooser(shareIntent, "Share link using"))
        }
    }

}