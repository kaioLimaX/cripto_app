package com.skymob.cryptoappexemple.domain.local.usecases

import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository

class CheckFavoriteUseCase(
    private val localCryptoRepository: LocalCryptoRepository

) {
    suspend fun execute(cryptoId: String): Boolean {

        val result = localCryptoRepository.checkIsFavorite(cryptoId)
        return result.getOrDefault(false)


    }
}