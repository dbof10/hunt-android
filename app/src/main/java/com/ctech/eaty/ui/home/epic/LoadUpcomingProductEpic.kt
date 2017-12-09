package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.annotation.MOBILE
import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.result.LoadUpcomingProductResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LoadUpcomingProductEpic(private val productRepository: ProductRepository,
                              private val appStore: Store<AppState>,
                              private val settingsManager: AppSettingsManager,
                              private val threadScheduler: ThreadScheduler) : Epic<HomeState> {

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<LoadUpcomingProductResult> {

        return action
                .filter {
                    it == HomeAction.LOAD_UPCOMING
                }
                .flatMap {
                    productRepository.getUpcomingProducts(null)
                            .map {
                                LoadUpcomingProductResult.success(it.map { UpcomingProductItemProps(it, isSaveModeEnabled()) })
                            }
                            .onErrorReturn {
                                LoadUpcomingProductResult.fail(it)
                            }
                            .subscribeOn(threadScheduler.workerThread())
                            .startWith(LoadUpcomingProductResult.inProgress())
                }
    }

    private fun isSaveModeEnabled(): Boolean {
        val appState = appStore.getState()
        return appState.connectionType == MOBILE && settingsManager.isDataServerEnabled()
    }
}
