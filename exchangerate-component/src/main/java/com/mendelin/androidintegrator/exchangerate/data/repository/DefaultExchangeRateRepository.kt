package com.mendelin.androidintegrator.exchangerate.data.repository

import com.mendelin.androidintegrator.exchangerate.data.api.ExchangeRateApi
import com.mendelin.androidintegrator.exchangerate.data.mapper.ExchangeRateMapper
import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate
import com.mendelin.androidintegrator.exchangerate.domain.repository.ExchangeRateRepository
import com.mendelin.androidintegrator.shared.AiResult
import kotlinx.coroutines.*

class DefaultExchangeRateRepository(
    private val exchangeRateApi: ExchangeRateApi,
    private val exchangeRateMapper: ExchangeRateMapper,
    private val ioDispatcher: CoroutineDispatcher
) : ExchangeRateRepository {
    override suspend fun getLatestValue(currency: String): AiResult<ExchangeRate, String> {
        return withContext(ioDispatcher) {
            val response = exchangeRateApi.getLatestValue(currency)
            response.reduce(
                success = {
                    AiResult.Success(exchangeRateMapper.transform(it, currency))
                },
                failure = {
                    AiResult.Failure(it)
                }
            )
        }
    }
}
