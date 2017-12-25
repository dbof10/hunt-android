package com.ctech.eaty.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.rx2.Rx2Apollo
import com.ctech.eaty.CreateUpcomingPageSubscriberMutation
import com.ctech.eaty.UpcomingPagesPageQuery
import com.ctech.eaty.UpcomingShowPageQuery
import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.entity.UpcomingDetail
import com.ctech.eaty.entity.UpcomingProduct
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.repository.mapper.ProductMapper
import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.type.CreateUpcomingPageSubscriberInput
import com.ctech.eaty.ui.topiclist.action.SearchBarCode
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable
import timber.log.Timber


class ProductRepository(private val homeStore: Store<ProductResponse, BarCode>,
                        private val productStore: Store<ProductDetailResponse, BarCode>,
                        private val searchStore: Store<ProductResponse, SearchBarCode>,
                        private val apiClient: ProductHuntApi,
                        private val apolloClient: ApolloClient,
                        private val mapper: ProductMapper,
                        private val appSettingsManager: AppSettingsManager) {
    fun getHomePosts(barcode: BarCode, forcedLoad: Boolean = false): Observable<ProductResponse> {
        val stream = if (forcedLoad) homeStore.fetch(barcode) else homeStore.get(barcode)
        return stream
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun getProductDetail(barcode: BarCode): Observable<ProductDetail> {
        return productStore.get(barcode)
                .map {
                    it.post
                }
                .toObservable()
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun purgeProductDetail(barcode: BarCode) {
        productStore.clear(barcode)
    }

    fun getPostsByTopic(barcode: SearchBarCode): Observable<List<Product>> {
        return searchStore.get(barcode)
                .toObservable()
                .map { it.products }
                .retryWhen(RefreshTokenFunc(apiClient, appSettingsManager))
    }

    fun getUpcomingProducts(cursor: String?): Observable<List<UpcomingProduct>> {

        val query = UpcomingPagesPageQuery.builder()
                .cursor(cursor)
                .build()

        val apolloCall = apolloClient.query(query)
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)

        return Rx2Apollo.from(apolloCall)
                .filter {
                    it.data() != null
                }
                .map {
                    mapper.toUpcomingProducts(it.data()!!.upcomingPages()!!)
                }

    }

    fun getUpcomingProductDetail(slug: String): Observable<UpcomingDetail> {

        val query = UpcomingShowPageQuery.builder()
                .slug(slug)
                .variant("a")
                .build()

        val apolloCall = apolloClient.query(query)
                .responseFetcher(ApolloResponseFetchers.CACHE_FIRST)

        return Rx2Apollo.from(apolloCall)
                .filter {
                    it.data() != null
                }
                .map {
                    mapper.toUpcomingDetail(it.data()!!)
                }
    }

    fun subscribeUpcomingProduct(id: String, email: String): Observable<Boolean> {
        val mutate = CreateUpcomingPageSubscriberMutation.builder()
                .input(
                        CreateUpcomingPageSubscriberInput.builder()
                                .email(email)
                                .source_kind("variant_a")
                                .upcoming_page_id(id)
                                .build()
                ).build()

        val apolloCall = apolloClient.mutate(mutate)

        return Rx2Apollo.from(apolloCall)
                .flatMap {
                    if (it.errors().isNotEmpty()) {
                        it.errors().forEach {
                            Timber.e(Throwable(it.message()))
                        }
                        return@flatMap Observable.error<Boolean>(Throwable(it.errors().first().message()))
                    } else {
                        return@flatMap Observable.just(true)
                    }
                }
    }


}