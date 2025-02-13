package com.mendelin.androidintegrator.binance.data.mapper

import com.mendelin.androidintegrator.binance.data.model.CryptoAvgPriceDto
import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice

internal interface BinanceMapper {
    fun transform(dto: CryptoAvgPriceDto): CryptoAvgPrice
}
