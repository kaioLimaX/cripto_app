package com.skymob.cryptoappexemple.domain.local.usecases

import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository

class RemoveCryptoFavoriteUseCase(
    private val localCryptoRepository: LocalCryptoRepository
) {
    suspend fun execute(cryptoId: String): Result<Unit> = localCryptoRepository.removeFavoriteCrypto(cryptoId)
}