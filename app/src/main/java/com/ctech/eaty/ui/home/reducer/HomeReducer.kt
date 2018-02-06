package com.ctech.eaty.ui.home.reducer

import com.ctech.eaty.base.redux.Reducer
import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.ui.home.model.DailyProducts
import com.ctech.eaty.ui.home.model.FeedFooter
import com.ctech.eaty.ui.home.model.FooterType
import com.ctech.eaty.ui.home.model.HomeFeed
import com.ctech.eaty.ui.home.model.Jobs
import com.ctech.eaty.ui.home.model.NewProducts
import com.ctech.eaty.ui.home.model.SuggestedProducts
import com.ctech.eaty.ui.home.model.SuggestedTopics
import com.ctech.eaty.ui.home.model.UpcomingProducts
import com.ctech.eaty.ui.home.result.CheckLoginResult
import com.ctech.eaty.ui.home.result.DisableDataSaverResult
import com.ctech.eaty.ui.home.result.LoadCollectionResult
import com.ctech.eaty.ui.home.result.LoadJobResult
import com.ctech.eaty.ui.home.result.LoadMoreResult
import com.ctech.eaty.ui.home.result.LoadNewPostResult
import com.ctech.eaty.ui.home.result.LoadResult
import com.ctech.eaty.ui.home.result.LoadSuggestedProductsResult
import com.ctech.eaty.ui.home.result.LoadTopicResult
import com.ctech.eaty.ui.home.result.LoadUpcomingProductResult
import com.ctech.eaty.ui.home.result.LoadUserResult
import com.ctech.eaty.ui.home.result.RefreshResult
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HorizontalAdsItemViewModel
import com.ctech.eaty.ui.home.viewmodel.StickyItemViewModel
import com.ctech.eaty.util.DateUtils
import org.joda.time.DateTime
import java.lang.IllegalArgumentException

class HomeReducer : Reducer<HomeState> {

    private val FB_AD_ID = "1966287263602613_2031648393733166"
    private val FB_AD_ID_2 = "1966287263602613_2032008040363868"
    private val SEARCH_NOT_FOUND = -1

    override fun apply(state: HomeState, result: Result): HomeState {
        when (result) {
            is LoadResult -> {
                return when {
                    result.loading -> state.copy(
                            loading = true,
                            loadError = null
                    )
                    result.error != null -> state.copy(
                            loading = false,
                            loadError = result.error)
                    else -> state.copy(
                            loading = false,
                            loadError = null,
                            content = listOf(DailyProducts(StickyItemViewModel(0, DateUtils.getRelativeTime(result.date)), result.content)))
                }
            }
            is RefreshResult -> {
                return when {
                    result.refreshing -> state.copy(refreshing = true)
                    result.error != null -> state.copy(
                            refreshing = false,
                            refreshError = result.error)
                    else -> state.copy(
                            refreshing = false,
                            refreshError = null,
                            loadError = null,
                            content = listOf(DailyProducts(StickyItemViewModel(0, DateUtils.getRelativeTime(result.date)), result.content)),
                            dayAgo = 0,
                            page = 0)
                }
            }
            is LoadMoreResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null
                    )
                    result.error != null -> state.copy(
                            loadingMore = false,
                            loadMoreError = result.error,
                            content = addError(state))
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            dayAgo = result.dayAgo,
                            page = result.page)
                }
            }
            is LoadNewPostResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null
                    )
                    result.error != null -> state.copy(
                            loadingMore = false,
                            loadMoreError = result.error,
                            content = addError(state))
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            page = result.page)
                }
            }
            is LoadSuggestedProductsResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null
                    )
                    result.error != null -> state.copy(
                            loadingMore = false,
                            loadMoreError = result.error,
                            content = addError(state))
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            page = result.page)
                }
            }
            is LoadUserResult -> {
                return state.copy(user = result.content)
            }
            is CheckLoginResult -> {
                return state.copy(user = result.content)
            }
            is DisableDataSaverResult -> {
                val feed = state.content.map {
                    if (it is DailyProducts) {
                        return@map it.copy(products = it.products.map { it.copy(saveMode = result.enabled) })
                    } else {
                        it
                    }
                }

                return state.copy(content = feed)

            }
            is LoadUpcomingProductResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null)
                    result.error != null -> state.copy(
                            loadingMore = false,
                            loadMoreError = result.error,
                            content = addError(state))
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            page = result.page
                    )
                }
            }
            is LoadTopicResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null)
                    result.error != null -> state.copy(
                            loadingMore = false,
                            loadMoreError = result.error,
                            content = addError(state))
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            page = result.page
                    )
                }
            }
            is LoadJobResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null)
                    result.error != null -> state.copy(
                            loadingMore = false,
                            loadMoreError = result.error,
                            content = addError(state))
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            page = result.page
                    )
                }
            }
            is LoadCollectionResult -> {
                return when {
                    result.loading -> state.copy(
                            loadingMore = true,
                            content = addLoading(state),
                            loadMoreError = null)
                    result.error != null -> {
                        return if (result.error is NoSuchElementException) {
                            state.copy(
                                    loadingMore = false,
                                    content = removeLoading(state),
                                    page = result.page
                            )
                        } else {
                            state.copy(
                                    loadingMore = false,
                                    loadMoreError = result.error,
                                    content = addError(state))
                        }
                    }
                    else -> state.copy(
                            loadingMore = false,
                            loadError = null,
                            refreshError = null,
                            loadMoreError = null,
                            content = addFeed(state, result),
                            page = result.page
                    )
                }
            }
            else -> {
                throw  IllegalArgumentException("Unknown result")
            }
        }
    }

    private fun addError(state: HomeState): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        return lastFeed.plus(FeedFooter(FooterType.ERROR))
    }

    private fun removeLoading(state: HomeState): List<HomeFeed> {
        return state.content.dropLast(1)
    }

    private fun addLoading(state: HomeState): List<HomeFeed> {
        return state.content.plus(FeedFooter(FooterType.LOADING))
    }

    private fun addFeed(state: HomeState, result: LoadUpcomingProductResult): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        var searchIndex = SEARCH_NOT_FOUND
        lastFeed.forEachIndexed { index, feed ->
            if (feed is UpcomingProducts) {
                searchIndex = index
                return@forEachIndexed
            }
        }
        if (searchIndex == SEARCH_NOT_FOUND) {
            return lastFeed.plus(UpcomingProducts(StickyItemViewModel(0, "Upcoming Products"), result.content))
        } else {
            return lastFeed.mapIndexed { index, feed ->
                if (index == searchIndex) {
                    return@mapIndexed UpcomingProducts(StickyItemViewModel(0, "Upcoming Products"), result.content)
                } else {
                    return@mapIndexed feed
                }
            }
        }
    }

    private fun addFeed(state: HomeState, result: LoadNewPostResult): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        return lastFeed.plus(NewProducts(StickyItemViewModel(0, "Popular this month"), result.content))
    }

    private fun addFeed(state: HomeState, result: LoadSuggestedProductsResult): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        return lastFeed.plus(SuggestedProducts(StickyItemViewModel(0, "Suggested products for you"), result.content))
    }

    private fun addFeed(state: HomeState, result: LoadTopicResult): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        return lastFeed.plus(SuggestedTopics(StickyItemViewModel(0, "Topic you may be interested"), result.content))
    }

    private fun addFeed(state: HomeState, result: LoadJobResult): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        return lastFeed.plus(Jobs(StickyItemViewModel(0, "Hot Start-up jobs"), result.content))
    }

    private fun addFeed(state: HomeState, result: LoadCollectionResult): List<HomeFeed> {
        val lastFeed = removeLoading(state)
        return lastFeed.plus(result.content!!)
    }

    private fun addFeed(state: HomeState, result: LoadMoreResult): List<HomeFeed> {

        val lastFeed = removeLoading(state)
        return when {
            result.dayAgo == 1 -> lastFeed.plus(DailyProducts(StickyItemViewModel(result.dayAgo, DateUtils.getRelativeTime(DateTime.now(), result.date)),
                    result.content, listOf(HorizontalAdsItemViewModel(result.dayAgo, FB_AD_ID))))
            result.dayAgo == 2 -> lastFeed.plus(DailyProducts(StickyItemViewModel(result.dayAgo, DateUtils.getRelativeTime(DateTime.now(), result.date)),
                    result.content, listOf(HorizontalAdsItemViewModel(result.dayAgo, FB_AD_ID_2))))
            else -> lastFeed.plus(DailyProducts(StickyItemViewModel(result.dayAgo, DateUtils.getRelativeTime(DateTime.now(), result.date)), result.content))
        }
    }
}