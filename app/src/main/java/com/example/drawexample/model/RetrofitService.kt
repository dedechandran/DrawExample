package com.example.drawexample.model

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
    @Multipart
    @POST("images?")
    fun upload(
        @Part photo: MultipartBody.Part
    ): Call<String>
}