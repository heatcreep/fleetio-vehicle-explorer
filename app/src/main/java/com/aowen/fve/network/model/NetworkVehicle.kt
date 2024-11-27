package com.aowen.fve.network.model

import kotlinx.serialization.Serializable
import com.aowen.fve.data.model.VehiclePageDetails
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.model.VehicleImage
import com.aowen.fve.data.model.VehicleType
import com.aowen.fve.data.model.toVehicleStatus
import kotlinx.serialization.SerialName

/**
 * Network representation of [VehiclePageDetails]
 */
@Serializable
data class NetworkVehicle(
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
    @SerialName("default_image_url_small")
    val defaultImageUrlSmall: String?,
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
)

fun NetworkVehicle.asVehicleListItem(
    imageWidth: Int = 360,
    imageHeight: Int = 180
): VehicleListItem {
    val regex = """resize=w:\d+,h:\d+""".toRegex()
    val resizedImageUrl =
        defaultImageUrlSmall?.replace(regex) { "resize=w:$imageWidth,h:$imageHeight" }
    return VehicleListItem(
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
            default = resizedImageUrl ?: "",
            large = defaultImageUrlLarge ?: "",
        ),
        primaryMeterReadout = "$primaryMeterValue $primaryMeterUnit",
        secondaryMeterReadout = secondaryMeterUnit?.let { "$secondaryMeterValue $it" },
    )
}
