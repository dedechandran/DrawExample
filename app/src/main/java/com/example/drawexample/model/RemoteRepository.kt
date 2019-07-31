package com.example.drawexample.model


import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val service: RetrofitService) {

    fun uploadImage(photo: MultipartBody.Part, remoteCallback: RemoteCallback<String>) {
        service.upload(photo).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                remoteCallback.onError(t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { remoteCallback.onSuccess(it) }
            }
        })
    }


    interface RemoteCallback<in T> {
        fun onSuccess(data: T)
        fun onError(t: Throwable)
    }
}