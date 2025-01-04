package com.skymob.cryptoappexemple.domain.local.usecases

import com.skymob.cryptoappexemple.data.local.FavoriteCryptoDao
import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite
import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository

class GetCryptoFavoritesUseCase(
    private val localCryptoRepository: LocalCryptoRepository
) {
    suspend fun execute(): Result<List<CryptoFavorite>> = localCryptoRepository.getFavoriteCryptos()
}


