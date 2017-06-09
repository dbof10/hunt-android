package com.ctech.eaty.ui.home.state

import com.ctech.eaty.entity.Category

data class TabState(val loading : Boolean, val error: Throwable?, val content: List<Category>)