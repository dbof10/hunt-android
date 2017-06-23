package com.ctech.eaty.ui.home.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.ui.home.action.BarCodeGenerator
import com.ctech.eaty.ui.home.epic.LoadEpic
import com.ctech.eaty.ui.home.epic.LoadMoreEpic
import com.ctech.eaty.ui.home.epic.RefreshEpic
import com.ctech.eaty.ui.home.reducer.HomeReducer
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class HomeModule {

    @ActivityScope
    @Provides
    fun provideCustomTabHelper(): CustomTabActivityHelper {
        return CustomTabActivityHelper()
    }

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideHomeStore(productRepository: ProductRepository, barCodeGenerator: BarCodeGenerator,
                         threadScheduler: ThreadScheduler): Store<HomeState> {
        return Store<HomeState>(HomeState(), HomeReducer(), arrayOf(LoadEpic(productRepository, barCodeGenerator, threadScheduler),
                RefreshEpic(productRepository, barCodeGenerator, threadScheduler),
                LoadMoreEpic(productRepository, barCodeGenerator, threadScheduler)))

    }

    @Provides
    fun provideHomeViewModel(store: Store<HomeState>): HomeViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return HomeViewModel(state)
    }
}