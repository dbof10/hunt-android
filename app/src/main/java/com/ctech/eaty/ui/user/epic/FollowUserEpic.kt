package com.ctech.eaty.ui.user.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.result.FollowUserResult
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class FollowUserEpic(private val apiClient: ProductHuntApi,
                     private val threadScheduler: ThreadScheduler) : Epic<UserDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UserDetailState>): Observable<FollowUserResult> {
        return action.ofType(UserAction.FollowUserAction::class.java)
                .flatMap {
                    val id = it.userId
                    val follow = it.follow
                    val stream: Observable<Any> = if (it.follow) apiClient.followUser(id) else apiClient.unfollowUser(id)
                             stream
                            .map {
                                FollowUserResult.success(follow)
                            }
                            .onErrorReturn {
                                FollowUserResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(FollowUserResult.inProgress())
                }
    }
}