package com.tps.challenge.network

import com.google.gson.GsonBuilder
import com.tps.challenge.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun createTPSService(): TPSCoroutineService {
    val gson = GsonBuilder().create()
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(TPSCoroutineService::class.java)
}