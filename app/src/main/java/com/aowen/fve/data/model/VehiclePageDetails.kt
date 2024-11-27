package com.aowen.fve.data.model


data class VehiclePageDetails(
    val startCursor: String? = null,
    val nextCursor: String? = null,
    val perPage: Int = 50,
    val estimatedRemainingCount: Int = 0,
    val vehicles: List<VehicleListItem>
)

data class VehicleListItem(
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
) {
    val combinedDescription = "$year $make $model"
}

data class VehicleImage(
    val default: String = "",
    val large: String = ""
)