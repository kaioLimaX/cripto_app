package com.skymob.cryptoappexemple.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skymob.cryptoappexemple.domain.local.model.CryptoFavorite

@Database(entities = [CryptoFavorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCryptoDao(): FavoriteCryptoDao
}