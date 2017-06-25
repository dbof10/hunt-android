package com.ctech.eaty.ui.collectiondetail.navigation

import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import io.reactivex.Completable
import javax.inject.Inject


@ActivityScope
class CollectionDetailNavigation @Inject constructor(private val context: CollectionDetailActivity) {

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

}