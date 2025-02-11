package com.mendelin.androidintegrator.exchangerate.domain.usescase

import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate
import com.mendelin.androidintegrator.shared.AiResult

fun interface GetLatestValue {
    suspend operator fun invoke(currency: String): AiResult<ExchangeRate, String>
}
