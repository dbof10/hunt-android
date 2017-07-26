package com.ctech.eaty.ui.productdetail.navigation

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsSession
import android.support.v4.content.ContextCompat
import android.util.Log
import com.ctech.eaty.R
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.entity.Media
import com.ctech.eaty.entity.User
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.gallery.view.GalleryActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.user.view.UserActivity
import com.ctech.eaty.ui.vote.view.VoteActivity
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class ProductDetailNavigation @Inject constructor(private val context: ProductDetailActivity) {

    fun toUrl(url: String, session: CustomTabsSession): Completable {
        return Completable.fromAction {
            val intentBuilder = CustomTabsIntent.Builder(session)
            intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(url), WebviewFallback())
        }

    }

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

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    fun toGallery(media: ArrayList<Media>): Completable{
        return Completable.fromAction {
            val intent = GalleryActivity.newIntent(context, media)
            context.startActivity(intent)
        }
    }

    fun toUser(user: User, option: ActivityOptions): Completable {
        return Completable.fromAction {
            val intent = UserActivity.newIntent(context, user)
            context.startActivity(intent, option.toBundle())
        }
    }

}