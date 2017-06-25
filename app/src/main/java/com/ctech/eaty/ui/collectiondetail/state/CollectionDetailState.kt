package com.ctech.eaty.ui.collectiondetail.state

import com.ctech.eaty.entity.CollectionDetail

data class CollectionDetailState(val loading: Boolean = false,
                                 val loadError: Throwable? = null,
                                 val content: CollectionDetail? = CollectionDetail.EMPTY)
