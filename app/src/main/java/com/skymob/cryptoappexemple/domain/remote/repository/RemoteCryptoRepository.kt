package com.skymob.cryptoappexemple.domain.remote.repository

import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import kotlinx.coroutines.flow.Flow

interface RemoteCryptoRepository {
    suspend fun getCryptoCurrencies(start: Int, limit: Int, convert: String): Flow<Result<List<Crypto>>>
}