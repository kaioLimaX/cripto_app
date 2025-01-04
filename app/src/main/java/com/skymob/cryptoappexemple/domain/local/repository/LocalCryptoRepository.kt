package com.skymob.cryptoappexemple.domain.local.repository

import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite

interface LocalCryptoRepository {
    suspend fun getFavoriteCryptos(): Result<List<CryptoFavorite>>
    suspend fun addFavoriteCrypto(crypto: CryptoFavorite) : Result<Unit>
    suspend fun removeFavoriteCrypto(cryptoId: String) : Result<Unit>
    suspend fun checkIsFavorite(cryptoId: String) : Result<Boolean>


}