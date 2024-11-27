package com.aowen.fve.data.model

import androidx.compose.ui.graphics.Color
import com.aowen.fve.R
import com.aowen.fve.ui.theme.BurntOrange

enum class VehicleStatus(
    val statusName: String,
    val badgeColor: Color,
) {
    ACTIVE("Active", Color.Green),
    INACTIVE("Inactive", Color.Blue),
    OUT_OF_SERVICE("Out of Service", Color.Red),
    IN_SHOP("In Shop", BurntOrange),
    UNKNOWN("Unknown", Color.Gray)
}

fun Int.toVehicleStatus(): VehicleStatus {
    return when (this) {
        637343 -> VehicleStatus.ACTIVE
        637345 -> VehicleStatus.INACTIVE
        637346 -> VehicleStatus.OUT_OF_SERVICE
        637344 -> VehicleStatus.IN_SHOP
        else -> VehicleStatus.UNKNOWN
    }
}