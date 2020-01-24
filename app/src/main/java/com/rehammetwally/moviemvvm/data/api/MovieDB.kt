package com.rehammetwally.moviemvvm.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

const val API_KEY = "e61b553903800d26d4fa4812fb894d53"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/"
const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20

object MovieDB {
    fun getInstance(): API {
        val interceptor = Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)


        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}