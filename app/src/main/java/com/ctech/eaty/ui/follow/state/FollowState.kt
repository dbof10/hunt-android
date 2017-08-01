package com.ctech.eaty.ui.follow.state

import com.ctech.eaty.entity.User

data class FollowState(val loading: Boolean = false, val loadingMore: Boolean = false,
                       val loadError: Throwable? = null,
                       val loadMoreError: Throwable? = null,
                       val content: List<User> = emptyList(), val page: Int = 1)
