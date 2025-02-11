package com.mendelin.androidintegrator.binance.presentation.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.mendelin.androidintegrator.binance.BinanceScreen
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes

fun NavGraphBuilder.binanceScreen() {
    composable(NavigationRoutes.Binance) {
        BinanceScreen()
    }
}
