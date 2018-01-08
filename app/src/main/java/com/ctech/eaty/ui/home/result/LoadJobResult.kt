package com.ctech.eaty.ui.home.result

import com.ctech.eaty.base.redux.Result
import com.ctech.eaty.entity.Job

data class LoadJobResult(val loading: Boolean = false, val error: Throwable? = null,
                         val content: List<Job> = emptyList(),
                         val page: Int = 1) : Result {
    companion object {
        fun inProgress(): LoadJobResult {
            return LoadJobResult(true)
        }

        fun success(content: List<Job>, page: Int): LoadJobResult {
            return LoadJobResult(content = content, page = page)
        }

        fun fail(throwable: Throwable): LoadJobResult {
            return LoadJobResult(error = throwable)
        }
    }
}