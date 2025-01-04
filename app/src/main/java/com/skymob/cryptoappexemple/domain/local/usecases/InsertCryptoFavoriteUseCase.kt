package com.skymob.cryptoappexemple.domain.local.usecases

import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite
import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository
import com.skymob.cryptoappexemple.domain.remote.model.Crypto

class InsertCryptoFavoriteUseCase(
    private val localCryptoRepository: LocalCryptoRepository
) {
    suspend fun execute(crypto: Crypto): Result<Unit> {
        val cryptoFavorite = CryptoFavorite(
            id = crypto.id.toString(),
            name = crypto.name,
            symbol = crypto.symbol,
            price = crypto.price
        )

        return localCryptoRepository.addFavoriteCrypto(cryptoFavorite)


    }
}