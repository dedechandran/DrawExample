package com.example.drawexample.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppCoreModule {
    @Singleton
    @Provides
    fun provideApplicationContext(app : Application) : Context = app
}