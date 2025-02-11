package com.mendelin.androidintegrator.binance.di

import com.mendelin.androidintegrator.binance.presentation.viewmodel.BinanceViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val binanceUiModule = module {
    viewModel { BinanceViewModel(get(), get(), get()) }
}
