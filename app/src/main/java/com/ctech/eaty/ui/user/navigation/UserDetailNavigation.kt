package com.ctech.eaty.ui.user.navigation

import android.app.ActivityOptions
import android.support.v4.content.ContextCompat
import android.widget.Button
import com.ctech.eaty.R
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.login.view.LoginActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.user.view.UserActivity
import com.ctech.eaty.util.MorphTransform
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class UserDetailNavigation @Inject constructor(private val context: UserActivity) {

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    fun toLogin(btFollow: Button): Completable {
        return Completable.fromAction {

            val intent = LoginActivity.newIntent(context)
            MorphTransform.addExtras(intent,
                    ContextCompat.getColor(context, R.color.hunt),
                    context.resources.getDimensionPixelSize(R.dimen.dialog_corners))
            val options = ActivityOptions.makeSceneTransitionAnimation(context, btFollow, context.getString(R.string.transition_user_login))
            context.startActivity(intent, options.toBundle())
        }
    }

}