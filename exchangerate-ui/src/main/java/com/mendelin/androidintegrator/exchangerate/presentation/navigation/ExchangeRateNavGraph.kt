package com.mendelin.androidintegrator.exchangerate.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mendelin.androidintegrator.exchangerate.presentation.ExchangeRateScreen
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes

fun NavGraphBuilder.exchangeRateScreen() {
    composable(NavigationRoutes.ExchangeRate) {
        ExchangeRateScreen()
    }
}
