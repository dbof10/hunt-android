package com.ctech.eaty.ui.ask.navigator

import android.content.Intent
import com.ctech.eaty.R
import com.ctech.eaty.ui.ask.view.AskActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import io.reactivex.Completable
import javax.inject.Inject

class AskNavigation @Inject constructor(private val context: AskActivity) {

    fun toShare(link: String): Completable {
        return Completable.fromAction {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_chooser)))
        }
    }

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }
}