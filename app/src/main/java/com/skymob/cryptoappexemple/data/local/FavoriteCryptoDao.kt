package com.skymob.cryptoappexemple.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite


@Dao
interface FavoriteCryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(crypto: CryptoFavorite) : Long

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<CryptoFavorite>

    @Query("DELETE FROM favorites WHERE id = :cryptoId")
    suspend fun removeFavorite(cryptoId: String) : Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :cryptoId)")
    suspend fun checkIsFavorite(cryptoId: String): Boolean
}