package com.skymob.cryptoappexemple.domain.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class CryptoFavorite(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val price: Double
)