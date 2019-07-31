package com.example.drawexample.ui

import com.example.drawexample.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @PerActivity
    @Provides
    fun provideMainPresenter(mainPresenter: MainPresenter) : MainContract.Presenter = mainPresenter
}