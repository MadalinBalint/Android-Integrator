package com.mendelin.androidintegrator.exchangerate.data.mapper

import com.mendelin.androidintegrator.exchangerate.data.model.GetLatestValueDto
import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate

internal class DefaultExchangeRateMapper : ExchangeRateMapper {
    override fun transform(dto: GetLatestValueDto, currency: String): ExchangeRate =
        ExchangeRate(
            currency = currency,
            value = dto.body.response.value
        )
}
