package com.ctech.eaty.ui.login.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.login.epic.LoadUserEpic
import com.ctech.eaty.ui.login.epic.RequestTokenEpic
import com.ctech.eaty.ui.login.navigation.LoginNavigation
import com.ctech.eaty.ui.login.reducer.LoginReducer
import com.ctech.eaty.ui.login.state.LoginState
import com.ctech.eaty.ui.login.viewmodel.LoginViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class LoginModule {

    @ActivityScope
    @Provides
    fun provideLoginStore(userRepository: UserRepository, threadScheduler: ThreadScheduler,
                          appSettings: AppSettingsManager): Store<LoginState> {
        return Store<LoginState>(LoginState(), LoginReducer(),
                arrayOf(LoadUserEpic(userRepository, threadScheduler, appSettings),
                        RequestTokenEpic(userRepository, threadScheduler, appSettings)))

    }

    @Provides
    fun provideLoginViewModel(store: Store<LoginState>, navigation: LoginNavigation, trackManager: FirebaseTrackManager): LoginViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return LoginViewModel(state, navigation, trackManager)
    }


}