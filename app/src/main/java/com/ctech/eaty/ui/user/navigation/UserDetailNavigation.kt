package com.ctech.eaty.ui.user.navigation

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.user.view.UserActivity
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

}