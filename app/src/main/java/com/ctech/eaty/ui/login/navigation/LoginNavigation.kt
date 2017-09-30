package com.ctech.eaty.ui.login.navigation

import android.app.Activity
import android.content.Intent
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.login.view.FacebookActivity
import com.ctech.eaty.ui.login.view.LoginActivity
import com.ctech.eaty.ui.login.view.TwitterActivity
import com.ctech.eaty.ui.profile.view.ProfileActivity
import io.reactivex.Completable
import javax.inject.Inject

@ActivityScope
class LoginNavigation @Inject constructor(private val context: LoginActivity) {
    companion object {
        val USER_KEY = "user"
        val FACEBOOK_REQUEST = 1002
        val TWITTER_REQUEST = 1003

    }

    fun toHome(user: UserDetail): Completable {
        return Completable.fromAction {
            val intent = Intent()
            intent.putExtra(USER_KEY, user)
            context.setResult(Activity.RESULT_OK, intent)
            context.finish()
        }
    }

    fun toFacebook(): Completable {
        return Completable.fromAction {
            val intent = Intent(context, FacebookActivity::class.java)
            context.startActivityForResult(intent, FACEBOOK_REQUEST)
        }
    }

    fun toTwitter(): Completable {
        return Completable.fromAction {
            val intent = Intent(context, TwitterActivity::class.java)
            context.startActivityForResult(intent, TWITTER_REQUEST)
        }
    }

    fun toEdit(): Completable {
        return Completable.fromAction {
            val intent = ProfileActivity.newIntent(context)
            context.startActivity(intent)
            context.finish()
        }
    }

}