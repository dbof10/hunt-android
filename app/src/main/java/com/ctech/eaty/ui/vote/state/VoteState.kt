package com.ctech.eaty.ui.vote.state

import com.ctech.eaty.entity.Vote

data class VoteState(val loading: Boolean = false, val loadingMore: Boolean = false,
                     val loadError: Throwable? = null,
                     val loadMoreError: Throwable? = null,
                     val content: List<Vote> = emptyList(), val page: Int = 1)
