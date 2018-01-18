package com.ctech.eaty.ui.topicdetail.navigation

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.topicdetail.view.TopicDetailActivity
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class TopicDetailNavigation @Inject constructor(private val context: TopicDetailActivity) {

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

}