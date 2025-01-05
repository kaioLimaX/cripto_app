package com.skymob.cryptoappexemple.domain.remote.usecases

import com.skymob.cryptoappexemple.domain.remote.model.CryptoLists
import com.skymob.cryptoappexemple.domain.remote.repository.RemoteCryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCryptoListsUseCase(
    private val repository: RemoteCryptoRepository
) {
    suspend fun execute(start: Int, limit: Int, convert: String): Flow<Result<CryptoLists>> {
        return repository.getCryptoCurrencies().map { result ->
            result.map { listCripto ->
                // Processando as listas
                val topCurrencies = listCripto.sortedByDescending { it.marketCap }.take(10)
                val losers = listCripto.sortedBy { it.percentChange24h }.take(10)
                val volumeCurrencies = listCripto.sortedByDescending { it.volume24h }.take(10)
                val gainers = listCripto.sortedByDescending { it.percentChange24h }

                CryptoLists(
                    topCurrencies = topCurrencies,
                    losers = losers,
                    volumeCurrencies = volumeCurrencies,
                    gainers = gainers
                )
            }
        }
    }
}