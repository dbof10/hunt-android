package com.ctech.eaty.ui.follow.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.follow.epic.LoadFollowerEpic
import com.ctech.eaty.ui.follow.epic.LoadFollowingEpic
import com.ctech.eaty.ui.follow.epic.LoadMoreFollowerEpic
import com.ctech.eaty.ui.follow.epic.LoadMoreFollowingEpic
import com.ctech.eaty.ui.follow.reducer.FollowReducer
import com.ctech.eaty.ui.follow.state.FollowState
import com.ctech.eaty.ui.follow.viewmodel.FollowViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class FollowModule {

    @ActivityScope
    @Provides
    fun provideCommentStore(userRepository: UserRepository,
                            threadScheduler: ThreadScheduler): Store<FollowState> {
        return Store<FollowState>(FollowState(), FollowReducer(), arrayOf(LoadFollowerEpic(userRepository, threadScheduler),
                LoadFollowingEpic(userRepository, threadScheduler), LoadMoreFollowerEpic(userRepository, threadScheduler),
                LoadMoreFollowingEpic(userRepository, threadScheduler)))

    }

    @Provides
    fun provideCommentViewModel(store: Store<FollowState>): FollowViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return FollowViewModel(state)
    }
}