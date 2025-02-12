package com.mendelin.androidintegrator.exchangerate.domain.usescase

import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate
import com.mendelin.androidintegrator.exchangerate.domain.repository.ExchangeRateRepository
import com.mendelin.androidintegrator.shared.AiResult

internal class GetLatestValueUseCase(
    private val exchangeRateRepository: ExchangeRateRepository
) : GetLatestValue {
    override suspend fun invoke(currency: String): AiResult<ExchangeRate, String> =
        exchangeRateRepository.getLatestValue(currency)
}
