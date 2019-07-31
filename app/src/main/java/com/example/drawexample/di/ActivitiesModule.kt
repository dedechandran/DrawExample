package com.example.drawexample.di

import com.example.drawexample.ui.MainActivity
import com.example.drawexample.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun provideMainActivity() : MainActivity
}