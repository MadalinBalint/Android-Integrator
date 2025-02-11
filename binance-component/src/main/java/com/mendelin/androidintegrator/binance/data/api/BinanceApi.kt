package com.mendelin.androidintegrator.binance.data.api

import com.mendelin.androidintegrator.binance.data.model.CryptoAvgPriceDto
import kotlinx.coroutines.flow.Flow

interface BinanceApi {
    fun getAveragePrice(symbols: List<String>, stableCoin: String): Flow<CryptoAvgPriceDto>
}
