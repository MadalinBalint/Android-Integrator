package com.mendelin.androidintegrator.binance.domain.usecase

import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice
import kotlinx.coroutines.flow.Flow

interface GetAveragePrice {
    operator fun invoke(symbols: List<String>, stableCoin: String): Flow<CryptoAvgPrice>
}
