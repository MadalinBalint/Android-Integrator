package com.mendelin.androidintegrator.binance.data.mapper

import com.mendelin.androidintegrator.binance.data.model.CryptoAvgPriceDto
import com.mendelin.androidintegrator.binance.domain.model.CryptoAvgPrice
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultBinanceMapperTest {
   private val sut = DefaultBinanceMapper()

    @Test
    fun `EXPECT CryptoAvgPrice WHEN mapping CryptoAvgPriceDto`() {
        val actual = sut.transform(CRYPTO_DTO)

        assertEquals(MAPPED_CRYPTO, actual)
    }

    private companion object {
        private val CRYPTO_DTO = CryptoAvgPriceDto(
            eventType = "avgPrice",
            eventTime = 1234567890L,
            symbol = "TRUMPUSDC",
            averagePriceInterval = "",
            averagePrice = 234.56,
            lastTradeTime = 1234567000L
        )

        private val MAPPED_CRYPTO = CryptoAvgPrice(
            eventType = "avgPrice",
            eventTime = 1234567890L,
            symbol = "TRUMPUSDC",
            averagePriceInterval = "",
            averagePrice = 234.56,
            lastTradeTime = 1234567000L
        )
    }
}
