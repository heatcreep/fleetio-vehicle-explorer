package com.aowen.fve.ui.screens.vehicles

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val VehiclesRoutePath = "vehicles"

fun NavController.navigateToVehicles(navOptions: NavOptions? = null) {
    this.navigate(VehiclesRoutePath, navOptions)
}

fun NavGraphBuilder.vehiclesScreen(
    navController: NavController
) {
    composable(
        route = VehiclesRoutePath,
    ) {
        VehiclesRoute(
            navController = navController
        )
    }
}