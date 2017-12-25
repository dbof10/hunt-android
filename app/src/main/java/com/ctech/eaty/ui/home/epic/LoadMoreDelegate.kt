package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.annotation.MOBILE
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.BarcodeGenerator
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.result.LoadUpcomingProductResult
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.home.viewmodel.UpcomingProductItemProps
import com.ctech.eaty.util.rx.ThreadScheduler
import io.reactivex.Observable

class LoadMoreDelegate(
        private val productRepository: ProductRepository,
        private val appStore: Store<AppState>,
        private val settingsManager: AppSettingsManager,
        private val threadScheduler: ThreadScheduler) {


    internal fun fetchUpcomingProducts(): Observable<LoadUpcomingProductResult> {
        return productRepository.getUpcomingProducts(null)
                .map {
                    LoadUpcomingProductResult.success(it.map { UpcomingProductItemProps(it, isSaveModeEnabled()) })
                }
                .onErrorReturn {
                    LoadUpcomingProductResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    internal fun fetchDailyProducts(dayAgo: Int): Observable<LoadMoreResult> {

        return productRepository.getHomePosts(BarcodeGenerator.createHomeNextBarCode(dayAgo))
                .compose(DataRefreshStrategyComposer(productRepository, dayAgo))
                .map {
                    LoadMoreResult.success(dayAgo, it.map { ProductItemViewModel(it, isSaveModeEnabled()) })
                }
                .onErrorReturn {
                    LoadMoreResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    private fun isSaveModeEnabled(): Boolean {
        val appState = appStore.getState()
        return appState.connectionType == MOBILE && settingsManager.isDataServerEnabled()
    }
}