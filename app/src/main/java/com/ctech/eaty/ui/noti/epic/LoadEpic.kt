package com.ctech.eaty.ui.noti.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.NotificationRepository
import com.ctech.eaty.ui.noti.action.BarCodeGenerator
import com.ctech.eaty.ui.noti.action.NotificationAction
import com.ctech.eaty.ui.noti.result.LoadResult
import com.ctech.eaty.ui.noti.state.NotificationState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadEpic(private val notificationRepository: NotificationRepository,
               private val barCodeGenerator: BarCodeGenerator,
               private val threadScheduler: ThreadScheduler) : Epic<NotificationState> {
    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<NotificationState>): Observable<LoadResult> {
        return action
                .filter {
                    it == NotificationAction.LOAD
                }
                .flatMap {
                    notificationRepository.getNotifications(barCodeGenerator.get(0))
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