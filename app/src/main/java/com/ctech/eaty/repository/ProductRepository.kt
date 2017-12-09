package com.ctech.eaty.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.rx2.Rx2Apollo
import com.ctech.eaty.UpcomingPagesPageQuery
import com.ctech.eaty.entity.ImageUrl
import com.ctech.eaty.entity.Product
import com.ctech.eaty.entity.ProductDetail
import com.ctech.eaty.entity.UpcomingProduct
import com.ctech.eaty.entity.User
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.response.ProductDetailResponse
import com.ctech.eaty.response.ProductResponse
import com.ctech.eaty.ui.topiclist.action.SearchBarCode
import com.nytimes.android.external.store3.base.impl.BarCode
import com.nytimes.android.external.store3.base.impl.Store
import io.reactivex.Observable


class ProductRepository(private val homeStore: Store<ProductResponse, BarCode>,
                        private val productStore: Store<ProductDetailResponse, BarCode>,
                        private val searchStore: Store<ProductResponse, SearchBarCode>,
                        private val apiClient: ProductHuntApi,
                        private val apolloClient: ApolloClient,
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
                .responseFetcher(ApolloResponseFetchers.CACHE_FIRST)

        return Rx2Apollo.from(apolloCall)
                .filter {
                    it.data() != null
                }
                .map {
                    it.data()!!.upcomingPages()!!.edges()!!.map { it.node()!! }
                }
                .map {
                    it.map {
                        val payload = it.fragments().upcomingPageItem()
                        UpcomingProduct(payload.id(), payload.name(), payload.tagline() ?: "", payload.background_image_uuid() ?: "",
                                payload.logo_uuid() ?: "", payload.subscriber_count(), payload.popular_subscribers()
                                .map {
                                    val userPayload = it.fragments().userSpotlight()
                                    val imageUrl = it.fragments().userSpotlight().fragments().userImageLink()
                                    User(userPayload.id().toInt(), userPayload.name(), userPayload.headline(), userPayload.username(),
                                            ImageUrl(px64 = imageUrl?._id() ?: ""))
                                })
                    }
                }
    }
}