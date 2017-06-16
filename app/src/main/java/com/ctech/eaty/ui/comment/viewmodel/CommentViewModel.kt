package com.ctech.eaty.ui.comment.viewmodel

import com.ctech.eaty.entity.Comment
import com.ctech.eaty.ui.comment.state.CommentState
import io.reactivex.Observable

class CommentViewModel(val stateDispatcher: Observable<CommentState>) {
    fun loading(): Observable<CommentState> {
        return stateDispatcher
                .filter { it.loading }
    }

    fun loadingMore(): Observable<CommentState> {
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
                .map(CommentState::loadMoreError)
    }

    fun content(): Observable<List<Comment>> {
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