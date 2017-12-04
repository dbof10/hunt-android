package com.ctech.eaty.ui.user.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.network.ProductHuntApi
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.user.epic.FollowUserEpic
import com.ctech.eaty.ui.user.epic.LoadEpic
import com.ctech.eaty.ui.user.epic.LoadProductEpic
import com.ctech.eaty.ui.user.epic.LoadRelationshipEpic
import com.ctech.eaty.ui.user.epic.LoreMoreProductEpic
import com.ctech.eaty.ui.user.navigation.UserDetailNavigation
import com.ctech.eaty.ui.user.reducer.UserDetailReducer
import com.ctech.eaty.ui.user.state.UserDetailState
import com.ctech.eaty.ui.user.viewmodel.UserDetailViewModel
import com.ctech.eaty.util.ResourceProvider
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides


@Module
class UserDetailModule {


    @ActivityScope
    @Provides
    fun provideUserDetailStore(apiClient: ProductHuntApi, userRepository: UserRepository,
                               threadScheduler: ThreadScheduler): Store<UserDetailState> {
        return Store(UserDetailState(), UserDetailReducer(),
                arrayOf(LoadEpic(userRepository, threadScheduler), LoadRelationshipEpic(apiClient, threadScheduler),
                        FollowUserEpic(apiClient, threadScheduler), LoadProductEpic(userRepository, threadScheduler),
                        LoreMoreProductEpic(userRepository, threadScheduler)))

    }

    @ActivityScope
    @Provides
    fun provideUserDetailViewModel(store: Store<UserDetailState>, userRepository: UserRepository, navigation: UserDetailNavigation,
                                   resourceProvider: ResourceProvider,
                                   threadScheduler: ThreadScheduler): UserDetailViewModel {
        return UserDetailViewModel(store.state, userRepository, navigation, threadScheduler, resourceProvider)
    }
}