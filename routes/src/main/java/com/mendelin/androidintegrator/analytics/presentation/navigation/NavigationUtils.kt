package com.mendelin.androidintegrator.analytics.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavController.navigateTo(destination: String) {
    navigate(destination) {
        if (currentDestination != null) {
            popUpTo(currentDestination!!.id) {
                saveState = true
                inclusive = true
            }
        } else {
            popUpTo(graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
        }

        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
