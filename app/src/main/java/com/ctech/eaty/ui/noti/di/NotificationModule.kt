package com.ctech.eaty.ui.noti.di

import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.NotificationRepository
import com.ctech.eaty.ui.noti.action.BarCodeGenerator
import com.ctech.eaty.ui.noti.epic.LoadEpic
import com.ctech.eaty.ui.noti.reducer.NotificationReducer
import com.ctech.eaty.ui.noti.state.NotificationState
import com.ctech.eaty.ui.noti.viewmodel.NotificationViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers


@Module
class NotificationModule {

    @Provides
    fun provideBarCodeGenerator(): BarCodeGenerator {
        return BarCodeGenerator()
    }

    @ActivityScope
    @Provides
    fun provideCommentStore(notificationRepository: NotificationRepository, barCodeGenerator: BarCodeGenerator,
                            threadScheduler: ThreadScheduler): Store<NotificationState> {
        return Store<NotificationState>(NotificationState(), NotificationReducer(), arrayOf(LoadEpic(notificationRepository, barCodeGenerator, threadScheduler)))

    }

    @Provides
    fun provideNotificationViewModel(store: Store<NotificationState>): NotificationViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return NotificationViewModel(state)
    }
}