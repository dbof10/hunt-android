package com.ctech.eaty.ui.search.view

import com.ctech.eaty.base.MvpView
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel

interface SearchView : MvpView {
    fun showLoading()
    fun showLoadError()
    fun showEmpty()
    fun showContent(content: List<ProductItemViewModel>)
    fun showLoadMoreError() {}
    fun showLoadingMore() {}

}