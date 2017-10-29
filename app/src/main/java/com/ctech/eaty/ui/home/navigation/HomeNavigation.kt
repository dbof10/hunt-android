package com.ctech.eaty.ui.home.navigation

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.ctech.eaty.R
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.ask.view.AskActivity
import com.ctech.eaty.ui.collection.view.CollectionActivity
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.job.view.JobListActivity
import com.ctech.eaty.ui.live.view.LiveEventActivity
import com.ctech.eaty.ui.login.view.LoginActivity
import com.ctech.eaty.ui.meetup.view.MeetupActivity
import com.ctech.eaty.ui.noti.view.NotificationActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.radio.view.RadioActivity
import com.ctech.eaty.ui.search.view.SearchActivity
import com.ctech.eaty.ui.topic.view.TopicActivity
import com.ctech.eaty.ui.user.view.UserActivity
import com.ctech.eaty.widget.transition.CircularTransform
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class HomeNavigation @Inject constructor(private val context: HomeActivity) {

    companion object {
        val LOGIN_REQUEST_CODE = 1001
    }

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    fun toLogin(transitionView: ImageView? = null): Completable {
        return Completable.fromAction {
            val intent = LoginActivity.newIntent(context)
            CircularTransform.addExtras(intent, Color.WHITE, R.drawable.ic_account_white)
            if (transitionView != null) {
                val options = ActivityOptions.makeSceneTransitionAnimation(context, transitionView, context.getString(R.string.transition_user_login))
                context.startActivityForResult(intent, LOGIN_REQUEST_CODE, options.toBundle())
                return@fromAction
            }
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
                R.id.action_live -> {
                    intent.setClass(context, LiveEventActivity::class.java)
                }
                R.id.action_jobs -> {
                    intent.setClass(context, JobListActivity::class.java)
                }
                R.id.action_radio -> {
                    intent.setClass(context, RadioActivity::class.java)
                }
                R.id.action_meetup -> {
                    intent.setClass(context, MeetupActivity::class.java)
                }
                R.id.action_ask -> {
                    intent.setClass(context, AskActivity::class.java)
                }
            }
            context.startActivity(intent)
        }
    }

    fun toNotification(): Completable {
        return Completable.fromAction {
            val intent = NotificationActivity.newIntent(context)
            context.startActivity(intent)
        }
    }

    fun toSearch(it: View): Completable {
        return Completable.fromAction {
            val options = ActivityOptions.makeSceneTransitionAnimation(context, it, context.getString(R.string.transition_search_back)).toBundle()
            context.startActivity(SearchActivity.newIntent(context), options)
        }
    }

}