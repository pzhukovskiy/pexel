package com.example.fetch.api

import com.example.fetch.data.Curated
import com.example.fetch.utilities.ACCESS_KEY
import com.example.fetch.utilities.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface FetchService {
    @Headers(ACCESS_KEY)
    @GET("curated")
    suspend fun getPhotos(): Curated

    companion object {
        fun create(): FetchService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FetchService::class.java)
        }
    }
}