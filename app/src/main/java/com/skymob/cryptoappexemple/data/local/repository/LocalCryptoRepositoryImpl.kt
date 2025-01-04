package com.skymob.cryptoappexemple.data.local.repository

import android.util.Log
import com.skymob.cryptoappexemple.data.local.FavoriteCryptoDao
import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite
import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository

class LocalCryptoRepositoryImpl(
    private val favoriteCryptoDao: FavoriteCryptoDao
) : LocalCryptoRepository {

    override suspend fun getFavoriteCryptos(): Result<List<CryptoFavorite>> {
        return try {
            val result = favoriteCryptoDao.getAllFavorites()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavoriteCrypto(crypto: CryptoFavorite): Result<Unit> {
        return try {
            val result = favoriteCryptoDao.addFavorite(crypto)
            if (result > 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Erro ao adicionar favorito"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFavoriteCrypto(cryptoId: String): Result<Unit> {
        return try {
            val rowsDeleted = favoriteCryptoDao.removeFavorite(cryptoId)
            if (rowsDeleted > 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Nenhum favorito encontrado para remover com ID: $cryptoId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkIsFavorite(cryptoId: String): Result<Boolean> {
        return try {
            val result = favoriteCryptoDao.checkIsFavorite(cryptoId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)

        }
    }
}