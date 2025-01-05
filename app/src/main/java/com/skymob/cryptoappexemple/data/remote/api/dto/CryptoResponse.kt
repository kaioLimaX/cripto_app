package com.skymob.cryptoappexemple.data.remote.api.dto

data class CryptoResponse(
    val `data`: List<Data> = listOf(),
    val status: Status = Status()
) {
    data class Data(
        val circulating_supply: Double = 0.0,
        val cmc_rank: Int = 0,
        val date_added: String = "",
        val id: Int = 0,
        val infinite_supply: Boolean = false,
        val last_updated: String = "",
        val max_supply: Double = 0.0,
        val name: String = "",
        val num_market_pairs: Int = 0,
        val platform: Platform = Platform(),
        val quote: Quote = Quote(),
        val self_reported_circulating_supply: Double = 0.0,
        val self_reported_market_cap: Double = 0.0,
        val slug: String = "",
        val symbol: String = "",
        val tags: List<String> = listOf(),
        val total_supply: Double = 0.0,
        val tvl_ratio: Double = 0.0
    ) {
        data class Platform(
            val id: Int = 0,
            val name: String = "",
            val slug: String = "",
            val symbol: String = "",
            val token_address: String = ""
        )

        data class Quote(
            val USD: USDQuote = USDQuote()
        ) {
            data class USDQuote(
                val fully_diluted_market_cap: Double = 0.0,
                val last_updated: String = "",
                val market_cap: Double = 0.0,
                val market_cap_dominance: Double = 0.0,
                val percent_change_1h: Double = 0.0,
                val percent_change_24h: Double = 0.0,
                val percent_change_30d: Double = 0.0,
                val percent_change_60d: Double = 0.0,
                val percent_change_7d: Double = 0.0,
                val percent_change_90d: Double = 0.0,
                val price: Double = 0.0,
                val tvl: Double = 0.0,
                val volume_24h: Double = 0.0,
                val volume_change_24h: Double = 0.0
            )
        }
    }

    data class Status(
        val credit_count: Int = 0,
        val elapsed: Int = 0,
        val error_code: Int = 0,
        val error_message: String? = null,
        val notice: Any = Any(),
        val timestamp: String = "",
        val total_count: Int = 0
    )
}