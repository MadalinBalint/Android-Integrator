package com.mendelin.androidintegrator.binance.data.model

import kotlinx.serialization.*

@Serializable
data class CryptoAvgPriceDto(
    @SerialName("e")
    val eventType: String,
    @SerialName("E")
    val eventTime: Long,
    @SerialName("s")
    val symbol: String,
    @SerialName("i")
    val averagePriceInterval: String,
    @SerialName("w")
    val averagePrice: Double,
    @SerialName("T")
    val lastTradeTime: Long
)
