package com.skymob.cryptoappexemple.domain.remote.repository

import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import kotlinx.coroutines.flow.Flow

interface RemoteCryptoRepository {
    suspend fun getCryptoCurrencies(
        start: Int = 1,
        limit: Int = 50,
        convert: String = "USD"
    ): Flow<Result<List<Crypto>>>
}