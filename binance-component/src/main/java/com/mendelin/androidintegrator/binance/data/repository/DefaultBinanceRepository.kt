package com.mendelin.androidintegrator.binance.data.repository

import com.mendelin.androidintegrator.binance.data.api.BinanceApi
import com.mendelin.androidintegrator.binance.data.mapper.BinanceMapper
import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice
import com.mendelin.androidintegrator.binance.domain.repository.BinanceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

internal class DefaultBinanceRepository(
    private val binanceApi: BinanceApi,
    private val binanceMapper: BinanceMapper,
    private val ioDispatcher: CoroutineDispatcher
) : BinanceRepository {
    override fun getAveragePrice(
        symbols: List<String>,
        stableCoin: String
    ): Flow<CryptoAvgPrice> {
        return flow {
            binanceApi.getAveragePrice(symbols, stableCoin)
                .flowOn(ioDispatcher)
                .collect {
                    emit(binanceMapper.transform(it))
                }
        }
    }
}
