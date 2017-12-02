package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.result.DisableDataSaverResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class DisableDataSaverEpic(private val appSettingsManager: AppSettingsManager,
                           private val threadScheduler: ThreadScheduler) : Epic<HomeState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<DisableDataSaverResult> {
        return action
                .filter { it == HomeAction.USE_MOBILE_DATA }
                .flatMap {
                    appSettingsManager.setDataSaver(false)
                    return@flatMap Observable.just(DisableDataSaverResult(appSettingsManager.isDataServerEnabled()))
                }
                .subscribeOn(threadScheduler.workerThread())

    }

}