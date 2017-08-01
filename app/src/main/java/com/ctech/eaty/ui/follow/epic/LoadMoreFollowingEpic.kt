package com.ctech.eaty.ui.follow.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.follow.action.FollowAction
import com.ctech.eaty.ui.follow.result.LoadMoreResult
import com.ctech.eaty.ui.follow.state.FollowState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadMoreFollowingEpic(private val userRepository: UserRepository,
                            private val threadScheduler: ThreadScheduler) : Epic<FollowState> {
    private val USER_LIMIT = 10

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<FollowState>): Observable<LoadMoreResult> {
        return action.ofType(FollowAction.LoadMoreFollowing::class.java)
                .flatMap {
                    val page = state.value.page + 1
                    userRepository.getFollowing(it.id, USER_LIMIT, page)
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