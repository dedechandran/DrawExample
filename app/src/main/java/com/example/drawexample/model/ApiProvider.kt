package com.example.drawexample.model

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiProvider @Inject constructor() {
    fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(initClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("http://192.168.2.187:5000/api/")
            .build()
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AppInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    private inner class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }
}