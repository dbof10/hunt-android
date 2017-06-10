package com.ctech.eaty.ui.comment.state

import com.ctech.eaty.entity.Comment

data class CommentState(val loading: Boolean = false, val loadingMore: Boolean = false,
                        val loadError: Throwable? = null,
                        val loadMoreError: Throwable? = null,
                        val content: List<Comment> = emptyList(), val page: Int = 1)
