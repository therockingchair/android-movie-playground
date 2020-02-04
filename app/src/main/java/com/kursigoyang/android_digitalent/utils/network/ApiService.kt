package com.kursigoyang.android_digitalent.utils.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kursigoyang.android_digitalent.utils.HttpLoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl



object ApiService {

  val instance: BaseApiService by lazy {

    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES)
      .writeTimeout(1, TimeUnit.MINUTES).build()

    val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(CoroutineCallAdapterFactory()).client(client).baseUrl("https://api.themoviedb.org/3/").build()

    return@lazy retrofit.create(BaseApiService::class.java)

  }
}