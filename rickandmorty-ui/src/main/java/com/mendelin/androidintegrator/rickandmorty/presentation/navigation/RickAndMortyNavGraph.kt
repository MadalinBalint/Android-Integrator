package com.mendelin.androidintegrator.rickandmorty.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mendelin.androidintegrator.rickandmorty.presentation.RickAndMortyScreen
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes

fun NavGraphBuilder.rickAndMortyScreen() {
    composable(NavigationRoutes.RickAndMorty) {
        RickAndMortyScreen()
    }
}
