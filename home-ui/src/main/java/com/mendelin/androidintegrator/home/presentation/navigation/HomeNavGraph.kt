package com.mendelin.androidintegrator.home.presentation.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.mendelin.androidintegrator.home.presentation.HomeScreen
import com.mendelin.androidintegrator.analytics.presentation.navigation.NavigationRoutes

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(NavigationRoutes.Home) {
        HomeScreen(navController = navController)
    }
}
