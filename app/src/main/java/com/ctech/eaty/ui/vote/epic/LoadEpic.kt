package com.ctech.eaty.ui.vote.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.ui.vote.action.BarCodeGenerator
import com.ctech.eaty.ui.vote.action.VoteAction
import com.ctech.eaty.ui.vote.result.LoadResult
import com.ctech.eaty.ui.vote.state.VoteState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(val voteRepository: VoteRepository,
               val barCodeGenerator: BarCodeGenerator,
               val threadScheduler: ThreadScheduler) : Epic<VoteState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<VoteState>): Observable<LoadResult> {
        return action.ofType(VoteAction.Load::class.java)
                .filter {
                    state.value.content.isEmpty()
                }
                .flatMap {
                    voteRepository.getVotes(barCodeGenerator.currentBarCode(it.id))
                            .map {
                                LoadResult.success(it)
                            }
                            .onErrorReturn {
                                LoadResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadResult.inProgress())
                }
    }
}