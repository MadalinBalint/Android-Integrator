package com.mendelin.androidintegrator.binance.data.mapper

import com.mendelin.androidintegrator.binance.data.model.CryptoAvgPriceDto
import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice

class DefaultBinanceMapper : BinanceMapper {
    override fun transform(dto: CryptoAvgPriceDto): CryptoAvgPrice =
        CryptoAvgPrice(
            eventType = dto.eventType,
            eventTime = dto.eventTime,
            symbol = dto.symbol,
            averagePriceInterval = dto.averagePriceInterval,
            averagePrice = dto.averagePrice,
            lastTradeTime = dto.lastTradeTime
        )
}
