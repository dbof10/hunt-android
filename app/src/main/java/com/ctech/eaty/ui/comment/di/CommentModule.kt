package com.ctech.eaty.ui.comment.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.CommentRepository
import com.ctech.eaty.ui.comment.epic.LoadEpic
import com.ctech.eaty.ui.comment.epic.LoadMoreEpic
import com.ctech.eaty.ui.comment.reducer.CommentReducer
import com.ctech.eaty.ui.comment.state.CommentState
import com.ctech.eaty.ui.comment.viewmodel.CommentViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class CommentModule {

    @ActivityScope
    @Provides
    fun provideCommentStore(commentRepository: CommentRepository,
                            threadScheduler: ThreadScheduler): Store<CommentState> {
        return Store<CommentState>(CommentState(), CommentReducer(), arrayOf(LoadEpic(commentRepository, threadScheduler),
                LoadMoreEpic(commentRepository, threadScheduler)))

    }

    @Provides
    fun provideCommentViewModel(store: Store<CommentState>): CommentViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return CommentViewModel(state)
    }
}