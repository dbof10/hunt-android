package com.ctech.eaty.ui.home.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.home.action.BarCodeGenerator
import com.ctech.eaty.ui.home.epic.*
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.reducer.HomeReducer
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.view.HomeActivity
import com.ctech.eaty.ui.home.viewmodel.HomeViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import com.facebook.litho.ComponentContext
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class HomeModule {


    @Provides
    fun provideComponentContext(activity: HomeActivity) = ComponentContext(activity)

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideHomeStore(productRepository: ProductRepository,
                         userRepository: UserRepository,
                         barCodeGenerator: BarCodeGenerator,
                         threadScheduler: ThreadScheduler): Store<HomeState> {
        return Store<HomeState>(HomeState(), HomeReducer(), arrayOf(LoadEpic(productRepository, barCodeGenerator, threadScheduler),
                RefreshEpic(productRepository, barCodeGenerator, threadScheduler),
                LoadUserEpic(userRepository, threadScheduler),
                LoadMoreEpic(productRepository, barCodeGenerator, threadScheduler), CheckLoginEpic()))

    }

    @ActivityScope
    @Provides
    fun provideHomeViewModel(store: Store<HomeState>, userRepository: UserRepository, navigation: HomeNavigation): HomeViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return HomeViewModel(state, userRepository, navigation)
    }
}