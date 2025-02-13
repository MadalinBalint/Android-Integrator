package com.mendelin.androidintegrator.exchangerate.domain.repository

import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate
import com.mendelin.androidintegrator.shared.AiResult

internal interface ExchangeRateRepository {
    suspend fun getLatestValue(currency: String): AiResult<ExchangeRate, String>
}
