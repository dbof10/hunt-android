package com.ctech.eaty.ui.follow.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.follow.action.FollowAction
import com.ctech.eaty.ui.follow.result.LoadResult
import com.ctech.eaty.ui.follow.state.FollowState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadFollowerEpic(val userRepository: UserRepository,
                       val threadScheduler: ThreadScheduler) : Epic<FollowState> {
    private val USER_LIMIT = 10
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<FollowState>): Observable<LoadResult> {
        return action.ofType(FollowAction.LoadFollower::class.java)
                .flatMap {
                    userRepository.getFollowers(it.id, USER_LIMIT, 1)
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