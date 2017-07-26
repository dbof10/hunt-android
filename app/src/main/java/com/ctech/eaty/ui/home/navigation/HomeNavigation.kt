package com.ctech.eaty.ui.home.navigation

import android.content.Intent
import com.ctech.eaty.R
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.collection.view.CollectionActivity
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.login.view.LoginActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.radio.view.RadioActivity
import com.ctech.eaty.ui.topic.view.TopicActivity
import com.ctech.eaty.ui.user.view.UserActivity
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class HomeNavigation @Inject constructor(private val context: HomeActivity) {

    companion object {
        val LOGIN_REQUEST_CODE = 1001
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

    fun toComment(id: Int): Completable {
        return Completable.fromAction {
            val intent = CommentActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    fun toLogin(): Completable {
        return Completable.fromAction {
            val intent = LoginActivity.newIntent(context)
            context.startActivityForResult(intent, LOGIN_REQUEST_CODE)
        }
    }

    fun toUser(user: UserDetail): Completable {
        return Completable.fromAction {
            val intent = UserActivity.newIntent(context, user)
            context.startActivity(intent)
        }
    }

    fun delegate(id: Int): Completable {
        return Completable.fromAction {
            val intent = Intent()
            when (id) {
                R.id.action_collection -> {
                    intent.setClass(context, CollectionActivity::class.java)
                }
                R.id.action_topic -> {
                    intent.setClass(context, TopicActivity::class.java)
                }
                R.id.action_radio -> {
                    intent.setClass(context, RadioActivity::class.java)
                }
            }
            context.startActivity(intent)
        }
    }

}