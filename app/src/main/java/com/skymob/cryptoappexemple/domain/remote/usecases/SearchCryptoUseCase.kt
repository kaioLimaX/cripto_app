package com.skymob.cryptoappexemple.domain.remote.usecases

import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository
import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import com.skymob.cryptoappexemple.domain.remote.repository.RemoteCryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchCryptoUseCase(
    private val repository: RemoteCryptoRepository
) {
    suspend fun execute(searchTerm: String): Flow<Result<List<Crypto>>> {
        return repository.getCryptoCurrencies(1, 50, "USD").map { result ->
            result.map { cryptoList ->
                // Filtra a lista por nome ou símbolo que contenham o termo
                cryptoList.filter { crypto ->
                    crypto.name.contains(searchTerm, ignoreCase = true) ||
                            crypto.symbol.contains(searchTerm, ignoreCase = true)
                }
            }
        }
    }
}