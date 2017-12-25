package com.ctech.eaty.ui.upcomingdetail.viewmodel

import android.graphics.Color
import android.net.Uri
import android.support.customtabs.CustomTabsSession
import android.util.Log
import com.ctech.eaty.entity.DisplayNodeType
import com.ctech.eaty.entity.UpcomingDetail
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps
import com.ctech.eaty.ui.upcomingdetail.navigation.UpcomingDetailNavigation
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import io.reactivex.Observable

class UpcomingDetailViewModel(private val stateDispatcher: Observable<UpcomingProductState>,
                              private val navigation: UpcomingDetailNavigation) {

    fun loading(): Observable<UpcomingProductState> {
        return stateDispatcher
                .filter { it.loading || it.subscribing }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError }
    }


    fun content(): Observable<UpcomingDetail> {
        return stateDispatcher
                .filter {
                    (!it.loading && !it.subscribing)
                            && it.loadError == null
                            && it.content != null
                }
                .map { it.content }
    }

    fun moreUpcomingProducts(): Observable<List<UpcomingProductItemProps>> {
        return content()
                .map {
                    it.upcomingProducts
                            .map {
                                UpcomingProductItemProps(it, false)
                            }
                }
    }

    fun successMessage(): Observable<String> {
        return content()
                .map {
                    it.successMessage.getDisplayNode()
                }
    }

    fun topSubscribers(): Observable<List<String>> {
        return content().map {
            it.topSubscribers.map { it.name }.take(3)
        }
                .filter {
                    it.isNotEmpty() && it.size >= 3
                }
    }

    fun messages(): Observable<List<MessageViewModel>> {
        return content()
                .distinctUntilChanged()
                .map {
                    val messages = ArrayList<MessageViewModel>()
                    val why = it.body.whyText.getDisplayNode()
                    val what = it.body.whatText.getDisplayNode()
                    val who = it.body.whoText.getDisplayNode()
                    messages.add(MessageViewModel(MessageViewModel.TYPE_DEFAULT, who))
                    messages.add(MessageViewModel(MessageViewModel.TYPE_DEFAULT, what))
                    messages.add(MessageViewModel(MessageViewModel.TYPE_EXTENDED, why, Color.parseColor(it.body.brandColor)))
                    return@map messages
                }

    }


    fun subscribed(): Observable<Boolean> {
        return stateDispatcher
                .filter {
                    it.subscribed
                }
                .map { it.subscribed }
                .distinctUntilChanged()

    }

    fun openLink(url: String, session: CustomTabsSession?) {
        val uri = Uri.parse(url)
        val type = try {
            DisplayNodeType.valueOf(uri.getQueryParameter("type"))
        } catch (e: Exception) {
            null
        }

        val payload = uri.getQueryParameter("payload")
        if (type == DisplayNodeType.VIDEO) {
            if (!payload.isNullOrEmpty()) {
                navigation.toYoutube(Uri.parse(payload))
                        .subscribe()
            }
        } else {
            navigation.toUrl(url, session)
                    .subscribe()
        }
    }
}