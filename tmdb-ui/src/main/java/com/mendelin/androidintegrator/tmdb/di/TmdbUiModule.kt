package com.mendelin.androidintegrator.tmdb.di

import com.mendelin.androidintegrator.tmdb.presentation.viewmodel.TmdbViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val tmdbUiModule = module {
    viewModel { TmdbViewModel(get(), get()) }
}
