package com.ctech.eaty.ui.collection.viewmodel

import com.ctech.eaty.entity.Collection
import com.ctech.eaty.ui.collection.state.CollectionState
import io.reactivex.Observable

class CollectionViewModel(private val stateDispatcher: Observable<CollectionState>) {
    fun loading(): Observable<CollectionState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<CollectionState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError }
    }


    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map(CollectionState::loadMoreError)
    }

    fun content(): Observable<List<Collection>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.loadingMore
                            && it.loadError == null
                            && it.loadMoreError == null
                            && it.content.isNotEmpty()

                }
                .map { it.content }
    }
}