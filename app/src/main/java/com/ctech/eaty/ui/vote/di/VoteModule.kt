package com.ctech.eaty.ui.vote.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.VoteRepository
import com.ctech.eaty.ui.vote.epic.LoadEpic
import com.ctech.eaty.ui.vote.epic.LoadMoreEpic
import com.ctech.eaty.ui.vote.reducer.VoteReducer
import com.ctech.eaty.ui.vote.state.VoteState
import com.ctech.eaty.ui.vote.viewmodel.VoteViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class VoteModule {

    @ActivityScope
    @Provides
    fun provideCommentStore(voteRepository: VoteRepository,
                            threadScheduler: ThreadScheduler): Store<VoteState> {
        return Store<VoteState>(VoteState(), VoteReducer(), arrayOf(LoadEpic(voteRepository, threadScheduler),
                LoadMoreEpic(voteRepository, threadScheduler)))

    }

    @Provides
    fun provideCommentViewModel(store: Store<VoteState>): VoteViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return VoteViewModel(state)
    }
}