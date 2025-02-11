package com.mendelin.androidintegrator.binance.domain.model

data class CryptoAvgPrice(
    val eventType: String,
    val eventTime: Long,
    val symbol: String,
    val averagePriceInterval: String,
    val averagePrice: Double,
    val lastTradeTime: Long
) {
    companion object {
        val EMPTY = CryptoAvgPrice(
            eventType = "",
            eventTime = 0L,
            symbol = "",
            averagePriceInterval = "",
            averagePrice = 0.0,
            lastTradeTime = 0L
        )
    }
}
