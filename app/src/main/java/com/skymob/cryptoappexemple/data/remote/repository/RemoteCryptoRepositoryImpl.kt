package com.skymob.cryptoappexemple.data.remote.repository

import com.skymob.cryptoappexemple.data.mapper.toDomain
import com.skymob.cryptoappexemple.data.remote.api.ApiService
import com.skymob.cryptoappexemple.data.remote.util.NetworkHelper
import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import com.skymob.cryptoappexemple.domain.remote.repository.RemoteCryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class RemoteCryptoRepositoryImpl(
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper
) : RemoteCryptoRepository {
    override suspend fun getCryptoCurrencies(
        start: Int,
        limit: Int,
        convert: String
    ): Flow<Result<List<Crypto>>> = flow {
        //check connection
        if (!networkHelper.isNetworkAvailable()) {
            emit(Result.failure(Exception("No internet connection")))
            return@flow//pausa execução do flow
        }
        val response = apiService.getCryptocurrencies()
        if (response.isSuccessful) {
            val cryptoResponse = response.body()
            if (cryptoResponse != null) {
                val cryptoList = cryptoResponse.data.map { it.toDomain() }
                emit(Result.success(cryptoList))
            } else {
                emit(Result.failure(Exception("Empty body is null")))
            }
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Failed to fetch cryptocurrencies"
            emit(Result.failure(Exception(errorMessage)))
        }


    }.catch {
        emit(Result.failure(Exception(it.message)))
    }


}
