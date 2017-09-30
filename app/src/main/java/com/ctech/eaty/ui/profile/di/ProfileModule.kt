package com.ctech.eaty.ui.profile.di

import android.support.v7.widget.Toolbar
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.ActivityScope
import com.ctech.eaty.repository.AppSettingsManager
import com.ctech.eaty.repository.UserRepository
import com.ctech.eaty.ui.profile.epic.SubmitEpic
import com.ctech.eaty.ui.profile.reducer.ProfileReducer
import com.ctech.eaty.ui.profile.state.ProfileState
import com.ctech.eaty.ui.profile.view.ProfileActivity
import com.ctech.eaty.ui.profile.viewmodel.ProfileViewModel
import com.ctech.eaty.util.rx.ThreadScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_profile.*


@Module
class ProfileModule {


    @ActivityScope
    @Provides
    fun provideToolbar(activity: ProfileActivity): Toolbar {
        return activity.toolbar
    }

    @ActivityScope
    @Provides
    fun provideProfileStore(userRepository: UserRepository,
                            appSettingsManager: AppSettingsManager,
                            threadScheduler: ThreadScheduler): Store<ProfileState> {
        return Store<ProfileState>(ProfileState(), ProfileReducer(),
                arrayOf(SubmitEpic(userRepository, appSettingsManager, threadScheduler)))

    }

    @Provides
    fun provideProfileViewModel(store: Store<ProfileState>): ProfileViewModel {
        val state = store.state
                .observeOn(AndroidSchedulers.mainThread())
        return ProfileViewModel(state)
    }

}