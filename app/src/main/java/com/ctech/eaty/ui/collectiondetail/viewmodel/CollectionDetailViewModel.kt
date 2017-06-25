package com.ctech.eaty.ui.collectiondetail.viewmodel

import android.util.Log
import com.ctech.eaty.entity.CollectionDetail
import com.ctech.eaty.ui.collectiondetail.state.CollectionDetailState
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import io.reactivex.Observable

class CollectionDetailViewModel(private val stateDispatcher: Observable<CollectionDetailState>) {
    fun loading(): Observable<CollectionDetailState> {
        return stateDispatcher
                .filter { it.loading }
    }


    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }


    fun content(): Observable<CollectionDetail> {
        return stateDispatcher
                .filter {
                    !it.loading && it.loadError == null
                }
                .flatMap {
                    if (it == null)
                        Observable.just(CollectionDetail.EMPTY)
                    else
                        Observable.just(it.content)
                }


    }

    fun header(): Observable<String> {
        return content()
                .map {
                    handleUrl(it.backgroundImageUrl)
                }
    }

    private fun handleUrl(url: String): String {
        return url
    }

    fun body(): Observable<List<CollectionDetailItemViewModel>> {
        return content()
                .map {
                    val body = ArrayList<CollectionDetailItemViewModel>()
                    body += mapHeader(it)
                    body += it.products.map {
                        ProductItemViewModel(it)
                    }
                    body
                }
    }

    private fun mapHeader(collection: CollectionDetail): CollectionHeaderItemViewModel = CollectionHeaderItemViewModel(collection)

}