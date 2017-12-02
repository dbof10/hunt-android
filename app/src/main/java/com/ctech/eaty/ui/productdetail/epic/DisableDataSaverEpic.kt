package com.ctech.eaty.ui.productdetail.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.productdetail.action.USE_MOBILE_DATA
import com.ctech.eaty.ui.productdetail.result.DisableDataSaverResult
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class DisableDataSaverEpic(private val appSettingsManager: AppSettingsManager,
                           private val threadScheduler: ThreadScheduler) : Epic<ProductDetailState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<ProductDetailState>): Observable<DisableDataSaverResult> {
        return action
                .ofType(USE_MOBILE_DATA::class.java)
                .flatMap {
                    appSettingsManager.setDataSaver(false)
                    return@flatMap Observable.just(DisableDataSaverResult(appSettingsManager.isDataServerEnabled()))
                }
                .subscribeOn(threadScheduler.workerThread())

    }

}