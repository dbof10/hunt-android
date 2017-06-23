package com.ctech.eaty.ui.vote.di

import com.ctech.eaty.ui.vote.view.VoteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class VoteFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): VoteFragment
}