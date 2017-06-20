package com.ctech.eaty.ui.productdetail.di

import com.ctech.eaty.ui.productdetail.view.ProductBodyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ProductDetailFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeProductCommentFragment(): ProductBodyFragment
}