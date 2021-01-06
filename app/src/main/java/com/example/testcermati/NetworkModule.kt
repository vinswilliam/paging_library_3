package com.example.testcermati

import com.example.testcermati.api.UserGithubService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> createRetrofit(clazz: Class<T>): T {

    val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }


    val okHttpClient: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
//        .addNetworkInterceptor(HttpLoggingInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl(UserGithubService.ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build().create(clazz)
}
