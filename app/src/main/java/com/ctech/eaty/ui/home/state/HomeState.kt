package com.ctech.eaty.ui.home.state

import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.home.viewmodel.HomeFeed

data class HomeState(val loading: Boolean = false, val loadingMore: Boolean = false,
                     val refreshing: Boolean = false, val loadError: Throwable? = null,
                     val loadMoreError: Throwable? = null, val refreshError: Throwable? = null,
                     val dayAgo: Int = 0,
                     val content: List<HomeFeed> = emptyList(), val user: UserDetail? = null)
