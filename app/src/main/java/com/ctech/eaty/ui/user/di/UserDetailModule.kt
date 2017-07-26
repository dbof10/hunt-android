package com.ctech.eaty.ui.user.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.user.action.BarCodeGenerator
import com.ctech.eaty.ui.user.epic.LoadEpic
import com.ctech.eaty.ui.user.navigation.UserDetailNavigation
import com.ctech.eaty.ui.user.reducer.UserDetailReducer
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.ui.user.viewmodel.UserDetailViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class UserDetailModule {

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideUserDetailStore(userRepository: UserRepository, barCodeGenerator: BarCodeGenerator,
                               threadScheduler: ThreadScheduler): Store<UserDetailState> {
        return Store<UserDetailState>(UserDetailState(), UserDetailReducer(),
                arrayOf(LoadEpic(userRepository, barCodeGenerator, threadScheduler)))

    }

    @ActivityScope
    @Provides
    fun provideUserDetailViewModel(store: Store<UserDetailState>, navigation: UserDetailNavigation): UserDetailViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return UserDetailViewModel(state, navigation)
    }
}