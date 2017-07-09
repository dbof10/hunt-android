package com.ctech.eaty.ui.comment.viewmodel

import com.ctech.eaty.ui.comment.state.CommentState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CommentViewModel(private val stateDispatcher: Observable<CommentState>) {

    private var comment: List<CommentItemViewModel> = emptyList()
    private val commentSubject: PublishSubject<List<CommentItemViewModel>> = PublishSubject.create()

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
                .filter { it.loadError != null && !it.loading }
                .map { it.loadError!! }
    }


    fun loadMoreError(): Observable<Throwable> {
        return stateDispatcher
                .filter { it.loadMoreError != null }
                .map { it.loadMoreError!! }
    }

    fun content(): Observable<List<CommentItemViewModel>> {
        return stateDispatcher
                .filter {
                    !it.loading
                            && !it.loadingMore
                            && it.loadError == null
                            && it.loadMoreError == null
                            && it.content.isNotEmpty()

                }
                .map {
                    comment = it.content.map { CommentItemViewModel(it) }
                    comment
                }
    }

    fun commentExpansion() = commentSubject

    fun selectCommentAt(position: Int) {

        comment = comment.mapIndexed { index, itemViewModel ->
            if (index == position) {
                itemViewModel.copy(expanded = !itemViewModel.isExpanded)
            } else {
                itemViewModel
            }
        }
        commentSubject.onNext(comment)
    }
}