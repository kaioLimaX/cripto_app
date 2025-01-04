package com.skymob.cryptoappexemple.di


import com.skymob.cryptoappexemple.data.remote.api.Constants
import com.skymob.cryptoappexemple.data.remote.api.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
    // Interceptor para log das requisições
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor) // Usa a classe ApiKeyInterceptor
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(apiKeyInterceptor: ApiKeyInterceptor): Retrofit {
    // Configuração do Retrofit com OkHttpClient configurado
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(provideOkHttpClient(apiKeyInterceptor))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
