package com.mendelin.androidintegrator.rickandmorty.di

import com.mendelin.androidintegrator.rickandmorty.presentation.viewmodel.RickAndMortyViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rickAndMortyUiModule = module {
    viewModel { RickAndMortyViewModel(get()) }
}
