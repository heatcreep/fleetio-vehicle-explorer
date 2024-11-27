package com.aowen.fve.network.model

import com.aowen.fve.data.model.VehicleDetails
import com.aowen.fve.data.model.VehicleImage
import com.aowen.fve.data.model.VehicleType
import com.aowen.fve.data.model.toVehicleStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkVehicleDetails(
    val id: Int,
    val name: String,
    val make: String,
    val model: String,
    @SerialName("license_plate")
    val licensePlate: String?,
    @SerialName("vehicle_type_id")
    val vehicleTypeId: Int,
    @SerialName("vehicle_type_name")
    val vehicleTypeName: String,
    val year: Int,
    val color: String?,
    @SerialName("default_image_url")
    val defaultImageUrl: String?,
    @SerialName("default_image_url_large")
    val defaultImageUrlLarge: String?,
    @SerialName("primary_meter_unit")
    val primaryMeterUnit: String,
    @SerialName("primary_meter_value")
    val primaryMeterValue: String,
    @SerialName("secondary_meter_unit")
    val secondaryMeterUnit: String?,
    @SerialName("secondary_meter_value")
    val secondaryMeterValue: String,
    @SerialName("vehicle_status_id")
    val vehicleStatusId: Int,
    val vin: String,
    val driver: NetworkDriver
)

fun NetworkVehicleDetails.asVehicleDetails(
): VehicleDetails {
    return VehicleDetails(
        id = id,
        name = name,
        make = make,
        model = model,
        type = VehicleType.entries.firstOrNull { type -> type.id == vehicleTypeId }
            ?: VehicleType.OTHER,
        licensePlate = licensePlate,
        year = year,
        color = color,
        status = vehicleStatusId.toVehicleStatus(),
        vin = vin,
        imageUrl = VehicleImage(
            default = defaultImageUrl ?: "",
            large = defaultImageUrlLarge ?: "",
        ),
        primaryMeterReadout = "$primaryMeterValue $primaryMeterUnit",
        secondaryMeterReadout = secondaryMeterUnit?.let { "$secondaryMeterValue $it" },
        driverDetails = driver.asDriverDetails()
    )
}