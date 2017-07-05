package com.ctech.eaty.linking

import android.net.Uri
import com.ctech.eaty.repository.HtmlParser
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Completable
import timber.log.Timber
import javax.inject.Inject

class UniversalLinkDispatcher @Inject constructor(private val htmlParser: HtmlParser, private val threadScheduler: ThreadScheduler,
                                                  val context: UniversalLinkActivity) {
    private val navigationMapper = mapOf(
            "" to 1,
            "posts" to 2
    )

    fun dispatch(url: String) {
        val navigation = getNavigation(url)
        when (navigation) {
            1 -> {
                toHome().subscribe()
            }

            2 -> {
                htmlParser.getProductIdBy(url)
                        .subscribeOn(threadScheduler.workerThread())
                        .flatMapCompletable { toProduct(it.toInt()) }
                        .observeOn(threadScheduler.uiThread())
                        .subscribe({}, Timber::e)
            }
        }
        context.finish()
    }

    private fun getNavigation(url: String): Int {
        val uri = Uri.parse(url)
        var route = uri.pathSegments.firstOrNull()

        if (route == null) {
            route = ""
        }
        return navigationMapper.getOrDefault(route, 1)
    }

    fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    fun toHome(): Completable {
        return Completable.fromAction {
            val intent = HomeActivity.newIntent(context)
            context.startActivity(intent)
        }
    }

}
