package com.skymob.cryptoappexemple.domain.remote.model

data class Crypto(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val percentChange24h: Double,
    val marketCap: Double,
    val volume24h: Double
)