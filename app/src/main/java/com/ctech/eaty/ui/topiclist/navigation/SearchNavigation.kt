package com.ctech.eaty.ui.topiclist.navigation

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.topiclist.view.TopicListActivity
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class SearchNavigation @Inject constructor(private val context: TopicListActivity) {

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

}