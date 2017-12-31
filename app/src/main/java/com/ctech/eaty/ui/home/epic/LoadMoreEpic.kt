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
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.result.LoadNewPostResult
import com.ctech.eaty.ui.home.result.LoadSuggestedProductsResult
import com.ctech.eaty.ui.home.result.LoadTopicResult
import com.ctech.eaty.ui.home.result.LoadUpcomingProductResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LoadMoreEpic(productRepository: ProductRepository,
                   appStore: Store<AppState>,
                   settingsManager: AppSettingsManager,
                   threadScheduler: ThreadScheduler) : Epic<HomeState> {

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
                    internalState = EpicProgress.IN_PROGRESS
                }
                .flatMap {
                    val currentState = state.value
                    val dayAgo = currentState.dayAgo + 1
                    val page = currentState.page + 1

                    when (page) {
                        1 -> return@flatMap loadStrategy.fetchUpcomingProducts(page)
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadUpcomingProductResult.inProgress())
                        5 -> return@flatMap loadStrategy.fetchNewProducts(page)
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadNewPostResult.inProgress())
                        7 -> return@flatMap loadStrategy.fetchSuggestedTopics(page)
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadTopicResult.inProgress())
                        9 -> return@flatMap loadStrategy.fetchSuggestedProducts(page)
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadSuggestedProductsResult.inProgress())

                        else -> return@flatMap loadStrategy.fetchDailyProducts(dayAgo, page)
                                .doOnNext {
                                    internalState = EpicProgress.IDLE
                                }
                                .startWith(LoadMoreResult.inProgress())
                    }
                }

    }


}