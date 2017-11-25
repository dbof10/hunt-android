package com.ctech.eaty.linking

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.repository.HtmlParser
import com.ctech.eaty.ui.collectiondetail.view.CollectionDetailActivity
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.productdetail.view.ProductDetailActivity
import com.ctech.eaty.ui.user.view.UserActivity
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.LinkMatcher
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class UniversalLinkDispatcher @Inject constructor(private val htmlParser: HtmlParser, private val threadScheduler: ThreadScheduler,
                                                  val context: UniversalLinkActivity) {
    private val HOME_ROUTE = 1
    private val POST_ROUTE = 2
    private val COLLECTION_ROUTE = 3
    private val ASK_ROUTE = 4
    private val USER_ROUTE = 5


    private val navigationMapper = mapOf(
            "" to HOME_ROUTE,
            "posts" to POST_ROUTE,
            "e" to COLLECTION_ROUTE,
            "ask" to ASK_ROUTE
    )

    private val NO_MATCH = -1

    fun dispatch(url: String): Completable {
        Timber.e("Receiving $url")
        return getNavigation(url)
                .flatMapCompletable {
                    when (it) {
                        POST_ROUTE -> {
                            htmlParser.getIdBy(url)
                                    .flatMapCompletable { toProduct(it.toInt()) }

                        }
                        COLLECTION_ROUTE -> {
                            htmlParser.getIdBy(url)
                                    .flatMapCompletable { toCollection(it.toInt()) }
                        }
                        ASK_ROUTE -> {
                            toUrl(url)
                        }
                        USER_ROUTE -> {
                            htmlParser.getIdBy(url)
                                    .flatMapCompletable { toUser(it.toInt()) }
                        }
                        else -> {
                            toHome()
                        }
                    }

                }
                .subscribeOn(threadScheduler.workerThread())
                .observeOn(threadScheduler.uiThread())

    }

    private fun toUrl(url: String): Completable {
        return Completable.fromAction {
            val intentBuilder = CustomTabsIntent.Builder()
                    .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))

            CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(url), WebviewFallback())
        }
    }

    private fun getNavigation(url: String): Observable<Int> {
        return Observable.fromCallable {
            val matchedRoute = inferRoute(url)
            if (matchedRoute != NO_MATCH) {
                return@fromCallable matchedRoute
            }
            val uri = Uri.parse(url)
            var route = uri.pathSegments.firstOrNull()

            if (route == null) {
                route = ""
            }
            return@fromCallable navigationMapper.getOrDefault(route, 1)
        }

    }

    private fun inferRoute(url: String) = when {
        LinkMatcher.matchCollections(url) -> COLLECTION_ROUTE
        LinkMatcher.matchUser(url) -> USER_ROUTE
        else -> NO_MATCH
    }

    private fun toCollection(id: Int): Completable {
        return Completable.fromAction {
            val intent = CollectionDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    private fun toProduct(id: Int): Completable {
        return Completable.fromAction {
            val intent = ProductDetailActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

    private fun toHome(): Completable {
        return Completable.fromAction {
            val intent = HomeActivity.newIntent(context)
            context.startActivity(intent)
        }
    }

    private fun toUser(id: Int): Completable {
        return Completable.fromAction {
            val intent = UserActivity.newIntent(context, id)
            context.startActivity(intent)
        }
    }

}
