package com.ctech.eaty.ui.productdetail.epic

import com.ctech.eaty.annotation.MOBILE
import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.productdetail.action.CHECK_DATA_SAVER
import com.ctech.eaty.ui.productdetail.result.CheckDataSaverResult
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class CheckDataSaverEpic(
        private val appStore: Store<AppState>,
        private val settingsManager: AppSettingsManager) : Epic<ProductDetailState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<ProductDetailState>): Observable<CheckDataSaverResult> {
        return action
                .ofType(CHECK_DATA_SAVER::class.java)
                .map {
                    CheckDataSaverResult(isSaveModeEnabled())
                }
    }

    private fun isSaveModeEnabled(): Boolean {
        val appState = appStore.getState()
        return appState.connectionType == MOBILE && settingsManager.isDataServerEnabled()
    }

}