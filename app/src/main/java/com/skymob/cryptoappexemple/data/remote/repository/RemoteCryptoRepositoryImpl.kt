package com.skymob.cryptoappexemple.data.remote.repository

import com.google.gson.Gson
import com.skymob.cryptoappexemple.data.mapper.toDomain
import com.skymob.cryptoappexemple.data.remote.api.ApiService
import com.skymob.cryptoappexemple.data.remote.api.dto.CryptoResponse
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
        val response = apiService.getCryptocurrencies(start, limit, convert)

        if (response.isSuccessful) {
            val cryptoResponse = response.body()
            if (cryptoResponse != null) {
                if (cryptoResponse.status.error_code == 0) {
                    val cryptoList = cryptoResponse.data?.map { it.toDomain() }.orEmpty()
                    emit(Result.success(cryptoList))
                } else {
                    // Erro da API: captura a mensagem de erro
                    emit(Result.failure(Exception(cryptoResponse.status.error_message.toString())))
                }
            } else {
                emit(Result.failure(Exception("Resposta da API está vazia.")))
            }
        } else {
            // Erro HTTP: captura o corpo do erro
            val errorBody = response.errorBody()?.string()
            val errorMessage = parseApiError(errorBody)
            emit(Result.failure(Exception(errorMessage)))
        }
    }.catch { exception ->
        emit(Result.failure(Exception("Erro inesperado: ${exception.localizedMessage}")))
    }

    private fun parseApiError(errorBody: String?): String {
        return try {
            if (errorBody.isNullOrEmpty()) {
                return "Erro desconhecido"
            }
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody, CryptoResponse::class.java)
            errorResponse.status.error_message ?: "Erro desconhecido"
        } catch (e: Exception) {
            "Erro desconhecido"
        }
    }


}
