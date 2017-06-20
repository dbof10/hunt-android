package com.ctech.eaty.ui.topic.viewmodel

import com.ctech.eaty.entity.Topic
import com.ctech.eaty.ui.topic.state.TopicState
import io.reactivex.Observable

class TopicViewModel(private val stateDispatcher: Observable<TopicState>) {
    fun loading(): Observable<TopicState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<TopicState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading}
                .map { it.loadError }
    }


    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map(TopicState::loadMoreError)
    }

    fun content(): Observable<List<Topic>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.loadingMore
                            && it.loadError == null
                            && it.loadMoreError == null
                }
                .map { it.content }
    }
}