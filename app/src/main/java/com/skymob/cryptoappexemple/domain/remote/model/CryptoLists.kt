package com.skymob.cryptoappexemple.domain.remote.model

data class CryptoLists(
    val topCurrencies: List<Crypto>,
    val losers: List<Crypto>,
    val volumeCurrencies: List<Crypto>,
    val gainers: List<Crypto>
)