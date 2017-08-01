package com.ctech.eaty.ui.follow.viewmodel

import com.ctech.eaty.entity.User
import com.ctech.eaty.ui.follow.state.FollowState
import io.reactivex.Observable

class FollowViewModel(private val stateDispatcher: Observable<FollowState>) {
    fun loading(): Observable<FollowState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<FollowState> {
        return stateDispatcher
                .filter { it.loadingMore }
    }

    fun loadError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError!! }
    }


    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map { it.loadMoreError!! }
    }

    fun content(): Observable<List<User>> {
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