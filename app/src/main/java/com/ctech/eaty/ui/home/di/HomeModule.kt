package com.ctech.eaty.ui.home.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.ProductRepository
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.app.AppState
import com.ctech.eaty.ui.home.epic.CheckLoginEpic
import com.ctech.eaty.ui.home.epic.DisableDataSaverEpic
import com.ctech.eaty.ui.home.epic.LoadEpic
import com.ctech.eaty.ui.home.epic.LoadMoreEpic
import com.ctech.eaty.ui.home.epic.LoadUpcomingProductEpic
import com.ctech.eaty.ui.home.epic.LoadUserEpic
import com.ctech.eaty.ui.home.epic.RefreshEpic
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
                         appStore: Store<AppState>,
                         settingsManager: AppSettingsManager,
                         threadScheduler: ThreadScheduler): Store<HomeState> {
        return Store(HomeState(), HomeReducer(),
                arrayOf(
                        LoadEpic(productRepository, appStore, settingsManager, threadScheduler),
                        RefreshEpic(productRepository, appStore, settingsManager, threadScheduler),
                        LoadUserEpic(userRepository, threadScheduler),
                        LoadMoreEpic(productRepository, appStore, settingsManager, threadScheduler),
                        CheckLoginEpic(),
                        DisableDataSaverEpic(settingsManager, threadScheduler),
                        LoadUpcomingProductEpic(productRepository, appStore, settingsManager, threadScheduler)
                ))

    }

    @ActivityScope
    @Provides
    fun provideHomeViewModel(store: Store<HomeState>, userRepository: UserRepository, navigation: HomeNavigation): HomeViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return HomeViewModel(state, userRepository, navigation)
    }
}