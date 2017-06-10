package com.ctech.eaty.ui.comment.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.CommentRepository
import com.ctech.eaty.ui.comment.action.BarCodeGenerator
import com.ctech.eaty.ui.comment.action.CommentAction
import com.ctech.eaty.ui.comment.result.LoadMoreResult
import com.ctech.eaty.ui.comment.state.CommentState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(val commentRepository: CommentRepository,
                   val barCodeGenerator: BarCodeGenerator,
                   val threadScheduler: ThreadScheduler) : Epic<CommentState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<CommentState>): Observable<LoadMoreResult> {
        return action.ofType(CommentAction.LoadMore::class.java)
                .filter {
                    !state.value.loadingMore
                }.
                flatMap {
                    val page = state.value.page + 1
                    commentRepository.getComments(barCodeGenerator.generateNextBarCode(it.id, page))
                            .map {
                                LoadMoreResult.success(page, it)
                            }
                            .onErrorReturn {
                                LoadMoreResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadMoreResult.inProgress())
                }
    }
}