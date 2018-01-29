package com.ctech.eaty.ui.vote.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.BarcodeGenerator.createVoteListBarCode
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.ui.vote.action.VoteAction
import com.ctech.eaty.ui.vote.result.LoadMoreResult
import com.ctech.eaty.ui.vote.state.VoteState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreEpic(private val voteRepository: VoteRepository,
                   private val threadScheduler: ThreadScheduler) : Epic<VoteState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<VoteState>): Observable<LoadMoreResult> {
        return action.ofType(VoteAction.LoadMore::class.java)
                .filter {
                    !state.value.loadingMore
                }.flatMap {
                    val page = state.value.page + 1
                    voteRepository.getVotes(createVoteListBarCode(it.id, page))
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