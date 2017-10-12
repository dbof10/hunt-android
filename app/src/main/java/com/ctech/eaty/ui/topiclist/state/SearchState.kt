package com.ctech.eaty.ui.topiclist.state

import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class SearchState(val loading: Boolean = false, val loadingMore: Boolean = false,
                       val loadError: Throwable? = null,
                       val loadMoreError: Throwable? = null,
                       val page: Int = 0,
                       val content: List<ProductItemViewModel> = emptyList())
