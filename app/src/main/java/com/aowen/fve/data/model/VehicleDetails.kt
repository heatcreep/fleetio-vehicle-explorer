package com.aowen.fve.data.model

data class VehicleDetails(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: VehicleImage = VehicleImage(),
    val make: String = "",
    val model: String = "",
    val type: VehicleType = VehicleType.OTHER,
    val licensePlate: String? = null,
    val year: Int = 0,
    val color: String? = "",
    val status: VehicleStatus = VehicleStatus.ACTIVE,
    val primaryMeterReadout: String = "",
    val secondaryMeterReadout: String? = null,
    val vin: String = "",
    val driverDetails: DriverDetails = DriverDetails()
) {
    val combinedDescription = "$year $make $model"
}