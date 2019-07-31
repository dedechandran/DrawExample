package com.example.drawexample.di

import android.app.Application
import com.example.drawexample.AppCore
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidInjectionModule::class),
        (AppCoreModule::class),
        (DataModule::class),
        (ActivitiesModule::class)]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: AppCore)
}