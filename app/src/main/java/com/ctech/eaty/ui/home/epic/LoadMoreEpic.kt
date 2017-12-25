package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.base.redux.Action
import com.ctech.eaty.base.redux.Epic
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.entity.EpicProgress
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.model.Cursor
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.result.LoadUpcomingProductResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LoadMoreEpic(private val productRepository: ProductRepository,
                   private val appStore: Store<AppState>,
                   private val settingsManager: AppSettingsManager,
                   private val threadScheduler: ThreadScheduler) : Epic<HomeState> {

    private val loadStrategy = LoadMoreDelegate(productRepository, appStore, settingsManager, threadScheduler)
    private var internalState = EpicProgress.IDLE
    private val ACTION_DEBOUNCE = 500L

    override fun apply(action: PublishSubject<Action>, state: BehaviorSubject<HomeState>): Observable<out Result> {
        return action
                .debounce(ACTION_DEBOUNCE, TimeUnit.MILLISECONDS)
                .filter {
                    it == HomeAction.LOAD_MORE
                            && internalState == EpicProgress.IDLE
                }
                .doOnNext {
                    internalState = EpicProgress.IN_PROGESS
                }
                .flatMap {
                    val currentState = state.value
                    val dayAgo = currentState.dayAgo + 1
                    val cursor = currentState.cursor

                    if (cursor == Cursor.UPCOMING) {
                        return@flatMap loadStrategy.fetchUpcomingProducts()
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadUpcomingProductResult.inProgress())

                    } else {
                        return@flatMap loadStrategy.fetchDailyProducts(dayAgo)
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadMoreResult.inProgress())
                    }
                }

    }


}