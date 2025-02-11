package com.mendelin.androidintegrator.home.di

import com.mendelin.androidintegrator.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeUiModule = module {
    viewModel { HomeViewModel(get()) }
}
