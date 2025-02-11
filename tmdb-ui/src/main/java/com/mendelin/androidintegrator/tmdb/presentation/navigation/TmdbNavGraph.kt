package com.mendelin.androidintegrator.tmdb.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes
import com.mendelin.androidintegrator.tmdb.presentation.TmdbScreen

fun NavGraphBuilder.tmdbScreen() {
    composable(NavigationRoutes.TMDB) {
        TmdbScreen()
    }
}
