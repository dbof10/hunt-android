package com.ctech.eaty.ui.comment.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.CommentRepository
import com.ctech.eaty.ui.comment.action.BarCodeGenerator
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

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideCommentStore(commentRepository: CommentRepository, barCodeGenerator: BarCodeGenerator,
                            threadScheduler: ThreadScheduler): Store<CommentState> {
        return Store<CommentState>(CommentState(), CommentReducer(), arrayOf(LoadEpic(commentRepository, barCodeGenerator, threadScheduler),
                LoadMoreEpic(commentRepository, barCodeGenerator, threadScheduler)))

    }

    @Provides
    fun provideCommentViewModel(store: Store<CommentState>): CommentViewModel {
        val state = store.getState()
                .observeOn(AndroidSchedulers.mainThread())
        return CommentViewModel(state)
    }
}