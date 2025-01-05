package com.skymob.cryptoappexemple.domain.remote.usecases

import com.skymob.cryptoappexemple.domain.remote.model.Crypto
import com.skymob.cryptoappexemple.domain.remote.repository.RemoteCryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCryptoInfoUseCase(
    private val repository: RemoteCryptoRepository
) {
    suspend fun execute(id: Int): Flow<Result<Crypto>> {
        return repository.getCryptoCurrencies().map { result ->
            result.map { cryptoList ->
                // Filtra o item da lista pelo ID
                cryptoList.find { it.id == id } // Retorna o item correspondente
                    ?: throw NoSuchElementException("Criptomoeda com ID $id não encontrada.")
            }
        }
    }
}