package com.ctech.eaty.ui.user.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.ProductHuntApi
import com.ctech.eaty.ui.user.action.UserAction
import com.ctech.eaty.ui.user.result.LoadRelationshipResult
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadRelationshipEpic(private val apiClient: ProductHuntApi,
                           private val threadScheduler: ThreadScheduler) : Epic<UserDetailState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<UserDetailState>): Observable<LoadRelationshipResult> {
        return action.ofType(UserAction.LoadRelationship::class.java)
                .flatMap {
                    val userId = it.userId
                    apiClient.getUserFollowingRL()
                            .map {
                                it.followingIds
                            }
                            .map {
                                it.contains(userId)
                            }
                            .map {
                                LoadRelationshipResult.success(it)
                            }
                            .onErrorReturn {
                                LoadRelationshipResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadRelationshipResult.inProgress())
                }
    }
}