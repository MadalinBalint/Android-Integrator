package com.mendelin.androidintegrator.exchangerate.presentation.viewmodel

import androidx.lifecycle.*
import com.mendelin.androidintegrator.exchangerate.domain.model.ExchangeRate
import com.mendelin.androidintegrator.exchangerate.domain.usescase.GetLatestValue
import com.mendelin.androidintegrator.presentation.viewmodel.*
import com.mendelin.androidintegrator.shared.AiResult
import kotlinx.coroutines.*

class ExchangeRateViewModel(
    private val getLatestValue: GetLatestValue,
    private val stateDelegate: StateDelegate<ExchangeRateState> = StateDelegate(ExchangeRateState.Idle)
) : ViewModel(), StateViewModel<ExchangeRateState> by stateDelegate {
    val currencies: StateDelegate<List<ExchangeRate>> = StateDelegate(emptyList())

    private val currencyList = mapOf(
        "USD" to "US dollar",
        "EUR" to "Euro",
        "JPY" to "Japanese yen",
        "GBP" to "Pound sterling",
        "AUD" to "Australian dollar",
        "CAD" to "Canadian dollar",
        "CHF" to "Swiss franc",
        "HKD" to "Hong Kong dollar",
        "NZD" to "New Zealand dollar",
        "RUB" to "Russian ruble"
    )

    fun getCurrencyList() {
        viewModelScope.launch {
            val list = buildList {
                currencyList.keys.forEach {
                    add(async { getLatestValue(it) })
                }
            }
            val result = list.awaitAll().filter { it is AiResult.Success }
                .map { (it as AiResult.Success).data }

            currencies.updateState { result }
        }
    }
}

sealed interface ExchangeRateState {
    data object Idle : ExchangeRateState
    data object Refresh : ExchangeRateState
    data object Loading : ExchangeRateState
    data class Error(val message: String) : ExchangeRateState
}
