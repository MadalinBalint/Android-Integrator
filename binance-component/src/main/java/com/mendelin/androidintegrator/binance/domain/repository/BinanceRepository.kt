package com.mendelin.androidintegrator.binance.domain.repository

import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice
import kotlinx.coroutines.flow.Flow

internal interface BinanceRepository {
    fun getAveragePrice(symbols: List<String>, stableCoin: String): Flow<CryptoAvgPrice>
}
