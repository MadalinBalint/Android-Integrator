package com.mendelin.androidintegrator.exchangerate.di

import com.mendelin.androidintegrator.exchangerate.presentation.viewmodel.ExchangeRateViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val exchangeRateUiModule = module {
    viewModel { ExchangeRateViewModel(get()) }
}
