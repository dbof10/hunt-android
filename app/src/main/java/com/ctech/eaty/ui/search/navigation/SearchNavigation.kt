package com.ctech.eaty.ui.search.navigation

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.comment.view.CommentActivity
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.search.view.SearchActivity
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class SearchNavigation @Inject constructor(private val context: SearchActivity) {

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

}