package com.ctech.eaty.ui.productdetail.view

import com.ctech.eaty.base.MvpView

interface ProductDetailView : MvpView {

    fun showLogin()
    fun showLiked(liked: Boolean)
}