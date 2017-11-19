package com.ctech.eaty.ui.home.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.UserRepository
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


    @ActivityScope
    @Provides
    fun provideHomeStore(productRepository: ProductRepository,
                         userRepository: UserRepository,
                         threadScheduler: ThreadScheduler): Store<HomeState> {
        return Store(HomeState(), HomeReducer(), arrayOf(LoadEpic(productRepository, threadScheduler),
                RefreshEpic(productRepository, threadScheduler),
                LoadUserEpic(userRepository, threadScheduler),
                LoadMoreEpic(productRepository, threadScheduler), CheckLoginEpic()))

    }

    @ActivityScope
    @Provides
    fun provideHomeViewModel(store: Store<HomeState>, userRepository: UserRepository, navigation: HomeNavigation): HomeViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return HomeViewModel(state, userRepository, navigation)
    }
}