package com.ctech.eaty.di

import android.content.Context
import com.ctech.eaty.base.EatyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(app: EatyApplication)

}