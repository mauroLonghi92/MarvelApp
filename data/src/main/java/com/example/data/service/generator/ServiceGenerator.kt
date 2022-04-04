package com.example.data.service.generator

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    )
        .addInterceptor { chain ->
        val defaultRequest = chain.request()

        val defaulthttpUrl = defaultRequest.url()

        val httpUrl = defaulthttpUrl.newBuilder()
            .addQueryParameter(PUBLIC_API_KEY, "b9baa830257d91dacc32db89d34d1f09")
            .addQueryParameter(PRIVATE_API_KEY, "3e207d05ea54389185beb3caa92ffc66")
            .addQueryParameter(TS, TS_VALUE)
            .build()

        val requestBuilder = defaultRequest.newBuilder().url(httpUrl)

        chain.proceed(requestBuilder.build())
    }
        .addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryOuts = 1

            while (!response.isSuccessful && tryOuts < 3) {
                Log.d(
                    this@ServiceGenerator.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                )
                tryOuts++
                response = chain.proceed(request)
            }

            response
        }

    private val builder = Retrofit.Builder()
        .baseUrl("http://gateway.marvel.com/public/")
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }

    companion object {
        private const val PRIVATE_API_KEY = "hash"
        private const val PUBLIC_API_KEY = "apikey"
        private const val TS = "ts"
        private const val TS_VALUE = "1"
    }
}