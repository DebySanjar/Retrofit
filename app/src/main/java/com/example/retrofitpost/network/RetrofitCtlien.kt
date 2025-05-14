package com.example.retrofitpost.network

import com.example.retrofitpost.network.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitCtlien {
    const val BASE_URL = "https://todoeasy.pythonanywhere.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun apiServise(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }
}