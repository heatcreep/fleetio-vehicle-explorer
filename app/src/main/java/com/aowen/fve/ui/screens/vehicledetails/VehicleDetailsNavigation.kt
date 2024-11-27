package com.aowen.fve.ui.screens.vehicledetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aowen.fve.ui.screens.vehicles.VehiclesRoute
import com.aowen.fve.ui.screens.vehicles.VehiclesRoutePath

const val VehicleDetailsRoutePath = "vehicle-details"

fun NavController.navigateToVehicleDetails(
    id: Int,
    navOptions: NavOptions? = null
) {
    this.navigate("$VehicleDetailsRoutePath/$id", navOptions)
}

fun NavGraphBuilder.vehicleDetailsScreen(
    navController: NavController
) {
    composable(
        route = "$VehicleDetailsRoutePath/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            },
        )
    ) {
        VehicleDetailsRoute(
            navController = navController
        )
    }
}