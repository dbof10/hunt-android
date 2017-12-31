package com.ctech.eaty.ui.home.epic

import com.ctech.eaty.annotation.MOBILE
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.entity.NEW_POSTS
import com.ctech.eaty.entity.NewPostCard
import com.ctech.eaty.entity.SUGGESTED_PRODUCTS
import com.ctech.eaty.entity.SUGGESTED_TOPIC
import com.ctech.eaty.entity.TopicCard
import com.ctech.eaty.entity.TopicsCard
import com.ctech.eaty.entity.UPCOMING_PAGE
import com.ctech.eaty.entity.UpcomingProductCard
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.BarcodeGenerator
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.result.LoadNewPostResult
import com.ctech.eaty.ui.home.result.LoadSuggestedProductsResult
import com.ctech.eaty.ui.home.result.LoadTopicResult
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


    internal fun fetchUpcomingProducts(page: Int): Observable<LoadUpcomingProductResult> {
        return productRepository.getHomeCard(UPCOMING_PAGE)
                .ofType(UpcomingProductCard::class.java)
                .map {
                    it.products
                }
                .map {
                    it.map { UpcomingProductItemProps(it, isSaveModeEnabled()) }
                }
                .map {
                    LoadUpcomingProductResult.success(it, page)
                }
                .onErrorReturn {
                    LoadUpcomingProductResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    internal fun fetchNewProducts(page: Int): Observable<LoadNewPostResult> {
        return productRepository.getHomeCard(NEW_POSTS)
                .ofType(NewPostCard::class.java)
                .map {
                    it.products
                }
                .map {
                    it.map { ProductItemViewModel(it, isSaveModeEnabled()) }
                }
                .map {
                    LoadNewPostResult.success(it, page)
                }
                .onErrorReturn {
                    LoadNewPostResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    internal fun fetchDailyProducts(dayAgo: Int, page: Int): Observable<LoadMoreResult> {

        return productRepository.getHomePosts(BarcodeGenerator.createHomeNextBarCode(dayAgo))
                .compose(DataRefreshStrategyComposer(productRepository, dayAgo))
                .map {
                    it.map { ProductItemViewModel(it, isSaveModeEnabled()) }
                }
                .map {
                    LoadMoreResult.success(it, dayAgo, page)
                }
                .onErrorReturn {
                    LoadMoreResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    internal fun fetchSuggestedTopics(page: Int): Observable<LoadTopicResult> {
        return productRepository.getHomeCard(SUGGESTED_TOPIC)
                .ofType(TopicsCard::class.java)
                .map {
                    it.topics
                }
                .map {
                    LoadTopicResult.success(it, page)
                }
                .onErrorReturn {
                    LoadTopicResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    internal fun fetchSuggestedProducts(page: Int): Observable<LoadSuggestedProductsResult> {
        return productRepository.getHomeCard(SUGGESTED_PRODUCTS)
                .ofType(TopicCard::class.java)
                .map {
                    it.products
                }
                .map {
                    it.map { ProductItemViewModel(it, isSaveModeEnabled()) }
                }
                .map {
                    LoadSuggestedProductsResult.success(it, page)
                }
                .onErrorReturn {
                    LoadSuggestedProductsResult.fail(it)
                }
                .subscribeOn(threadScheduler.workerThread())
    }

    private fun isSaveModeEnabled(): Boolean {
        val appState = appStore.getState()
        return appState.connectionType == MOBILE && settingsManager.isDataServerEnabled()
    }
}