package com.aowen.fve.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aowen.fve.ui.screens.vehicledetails.vehicleDetailsScreen
import com.aowen.fve.ui.screens.vehicles.VehiclesRoutePath
import com.aowen.fve.ui.screens.vehicles.vehiclesScreen

@Composable
fun FveNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = VehiclesRoutePath,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        vehiclesScreen(navController = navController)
        vehicleDetailsScreen(navController = navController)
    }
}