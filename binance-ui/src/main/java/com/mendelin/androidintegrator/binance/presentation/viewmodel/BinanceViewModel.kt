package com.mendelin.androidintegrator.binance.presentation.viewmodel

import androidx.lifecycle.*
import com.mendelin.androidintegrator.binance.domain.usecase.GetAveragePrice
import com.mendelin.androidintegrator.presentation.viewmodel.*
import com.mendelin.androidintegrator.remoteconfig.domain.usecase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

class BinanceViewModel(
    private val getBinanceInitialRemoteConfig: GetBinanceInitialRemoteConfig,
    private val observeBinanceRemoteConfig: ObserveBinanceRemoteConfig,
    private val getAveragePrice: GetAveragePrice,
    private val stateDelegate: StateDelegate<Map<String, Double>> = StateDelegate(emptyMap())
) : ViewModel(), StateViewModel<Map<String, Double>> by stateDelegate {
    private val cryptoMap = mutableMapOf<String, Double>()
    private var cryptoJob: Job? = null

    private val cryptoSymbols =
        listOf("BTC", "ETH", "LTC", "XRP", "DOGE", "TRUMP", "SOL", "ADA", "SHIB", "DOT", "TRX", "EGLD")

    fun observeStableCoin() {
        observeBinanceRemoteConfig().onEach {
            if (it.isNotEmpty()) {
                Timber.d("Updated stablecoin = $it")
                stopResults()
                getResults(it)
            }
        }.launchIn(viewModelScope)
    }

    fun getInitialConfig() {
        viewModelScope.launch {
            getBinanceInitialRemoteConfig().reduce(
                onSuccess = {
                    Timber.d("Initial stablecoin = $it")
                    getResults(it)
                },
                onFailure = {
                    Timber.e("Failed getting initial stablecoin. Defaulting to $DEFAULT_STABLECOIN")
                    getResults(DEFAULT_STABLECOIN)
                }
            )
        }
    }

    private fun getResults(initialStablecoin: String) {
        cryptoJob = viewModelScope.launch {
            getAveragePrice(cryptoSymbols, initialStablecoin)
                .catch {
                    Timber.e( it.localizedMessage ?: "Unknown exception")
                }
                .collect {
                    cryptoMap[it.symbol] = it.averagePrice
                    stateDelegate.updateState { cryptoMap.toSortedMap() }
                }
        }
    }

    fun stopResults() {
        cryptoJob?.cancel()
        cryptoJob = null
        cryptoMap.clear()

        Timber.d("Crypto job stopped")
    }

    override fun onCleared() {
        super.onCleared()
        stopResults()
    }

    private companion object {
        const val DEFAULT_STABLECOIN = "USDT"
    }
}
