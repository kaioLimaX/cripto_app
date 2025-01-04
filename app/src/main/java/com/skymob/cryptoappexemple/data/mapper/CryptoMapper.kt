package com.skymob.cryptoappexemple.data.mapper

import com.skymob.cryptoappexemple.data.remote.api.dto.CryptoResponse
import com.skymob.cryptoappexemple.domain.remote.model.Crypto

//extenção
fun CryptoResponse.Data.toDomain(): Crypto {
    return Crypto(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        price = this.quote.USD.price,
        percentChange24h = this.quote.USD.percent_change_24h,
        marketCap = this.quote.USD.market_cap,
        volume24h = this.quote.USD.volume_24h
    )
}