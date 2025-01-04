package com.skymob.cryptoappexemple.data.remote.api.interceptor

import com.skymob.cryptoappexemple.data.remote.api.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("X-CMC_PRO_API_KEY", Constants.APIKEY) // Adiciona a API Key no cabeçalho
            .build()
        return chain.proceed(modifiedRequest)
    }
}