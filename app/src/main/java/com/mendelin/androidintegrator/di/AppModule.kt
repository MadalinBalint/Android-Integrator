package com.mendelin.androidintegrator.di

import com.mendelin.androidintegrator.presentation.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
}
