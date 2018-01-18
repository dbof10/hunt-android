package com.ctech.eaty.ui.topicdetail.viewmodel

import android.util.Log
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.topicdetail.state.TopicDetailState
import io.reactivex.Observable

class TopicDetailViewModel(private val stateDispatcher: Observable<TopicDetailState>) {
    fun loading(): Observable<TopicDetailState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<TopicDetailState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null }
                .map { it.loadError }
    }

    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map { it.loadMoreError }
    }


    fun content(): Observable<List<ProductItemViewModel>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.loadingMore
                            && it.loadError == null
                            && it.loadMoreError == null
                            && it.content.isNotEmpty()
                }
                .map {
                    it.content
                }


    }
}