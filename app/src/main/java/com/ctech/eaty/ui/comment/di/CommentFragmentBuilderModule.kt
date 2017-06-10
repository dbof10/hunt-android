package com.ctech.eaty.ui.comment.di

import com.ctech.eaty.ui.comment.view.CommentFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class CommentFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): CommentFragment
}