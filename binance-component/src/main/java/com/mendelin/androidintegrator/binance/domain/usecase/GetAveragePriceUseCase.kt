package com.mendelin.androidintegrator.binance.domain.usecase

import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice
import com.mendelin.androidintegrator.binance.domain.repository.BinanceRepository
import kotlinx.coroutines.flow.Flow

class GetAveragePriceUseCase(
    private val binanceRepository: BinanceRepository
) : GetAveragePrice {
    override fun invoke(symbols: List<String>, stableCoin: String): Flow<CryptoAvgPrice> =
        binanceRepository.getAveragePrice(symbols, stableCoin)
}
