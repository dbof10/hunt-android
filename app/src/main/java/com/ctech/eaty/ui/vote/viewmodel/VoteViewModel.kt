package com.ctech.eaty.ui.vote.viewmodel

import com.ctech.eaty.entity.Vote
import com.ctech.eaty.ui.vote.state.VoteState
import io.reactivex.Observable

class VoteViewModel(private val stateDispatcher: Observable<VoteState>) {
    fun loading(): Observable<VoteState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<VoteState> {
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
                .map(VoteState::loadMoreError)
    }

    fun content(): Observable<List<Vote>> {
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