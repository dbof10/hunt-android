package com.ctech.eaty.ui.collection.state

import com.ctech.eaty.entity.Collection

data class CollectionState(val loading: Boolean = false, val loadingMore: Boolean = false,
                           val loadError: Throwable? = null,
                           val loadMoreError: Throwable? = null,
                           val content: List<Collection> = emptyList(), val page: Int = 1)
