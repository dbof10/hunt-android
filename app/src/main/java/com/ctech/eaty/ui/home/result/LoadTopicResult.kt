package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Topic

data class LoadTopicResult(val loading: Boolean = false, val error: Throwable? = null,
                           val content: List<Topic> = emptyList(),
                           val page: Int = 1) : Result {
    companion object {
        fun inProgress(): LoadTopicResult {
            return LoadTopicResult(true)
        }

        fun success(content: List<Topic>, page: Int): LoadTopicResult {
            return LoadTopicResult(content = content, page = page)
        }

        fun fail(throwable: Throwable): LoadTopicResult {
            return LoadTopicResult(error = throwable)
        }
    }
}