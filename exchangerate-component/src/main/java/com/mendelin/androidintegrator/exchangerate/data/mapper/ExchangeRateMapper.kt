package com.mendelin.androidintegrator.exchangerate.data.mapper

import com.mendelin.androidintegrator.exchangerate.data.model.GetLatestValueDto
import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate

interface ExchangeRateMapper {
    fun transform(dto: GetLatestValueDto, currency: String): ExchangeRate
}
