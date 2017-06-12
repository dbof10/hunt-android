package com.ctech.eaty.ui.topic.state

import com.ctech.eaty.entity.Topic


data class TopicState(val loading: Boolean = false, val loadingMore: Boolean = false,
                      val loadError: Throwable? = null,
                      val loadMoreError: Throwable? = null,
                      val content: List<Topic> = emptyList(), val page: Int = 1)
