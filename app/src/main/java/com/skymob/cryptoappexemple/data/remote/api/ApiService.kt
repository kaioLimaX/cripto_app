package com.skymob.cryptoappexemple.data.remote.api

import com.skymob.cryptoappexemple.data.remote.api.dto.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cryptocurrency/listings/latest")
    suspend fun getCryptocurrencies(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 50,
        @Query("convert") convert: String = "USD"
    ): Response<CryptoResponse>


}