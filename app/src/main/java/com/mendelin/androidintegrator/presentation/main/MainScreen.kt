package com.mendelin.androidintegrator.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.mendelin.androidintegrator.binance.presentation.navigation.binanceScreen
import com.mendelin.androidintegrator.exchangerate.presentation.navigation.exchangeRateScreen
import com.mendelin.androidintegrator.home.presentation.navigation.homeScreen
import com.mendelin.androidintegrator.rickandmorty.presentation.navigation.rickAndMortyScreen
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes
import com.mendelin.androidintegrator.tmdb.presentation.navigation.tmdbScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.Home,
            modifier = Modifier.padding(it)
        ) {
            homeScreen(navController = navController)

            rickAndMortyScreen()

            tmdbScreen()

            exchangeRateScreen()

            binanceScreen()
        }
    }
}
