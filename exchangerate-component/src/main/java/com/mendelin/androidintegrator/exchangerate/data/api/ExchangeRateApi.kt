package com.mendelin.androidintegrator.exchangerate.data.api

import com.mendelin.androidintegrator.exchangerate.data.model.GetLatestValueDto
import com.mendelin.androidintegrator.shared.AiResult

interface ExchangeRateApi {
    suspend fun getLatestValue(currency: String): AiResult<GetLatestValueDto, String>
}
