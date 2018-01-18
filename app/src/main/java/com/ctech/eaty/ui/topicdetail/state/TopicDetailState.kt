package com.ctech.eaty.ui.topicdetail.state

import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

data class TopicDetailState(val loading: Boolean = false, val loadingMore: Boolean = false,
                            val loadError: Throwable? = null,
                            val loadMoreError: Throwable? = null,
                            val page: Int = 1,
                            val content: List<ProductItemViewModel> = emptyList())
