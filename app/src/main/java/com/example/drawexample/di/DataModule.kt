package com.example.drawexample.di

import com.example.drawexample.model.ApiProvider
import com.example.drawexample.model.RetrofitService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideRetrofitService(apiProvider: ApiProvider): RetrofitService =
        apiProvider.initRetrofit().create(RetrofitService::class.java)
}