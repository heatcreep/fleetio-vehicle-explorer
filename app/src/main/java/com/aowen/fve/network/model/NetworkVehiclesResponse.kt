package com.aowen.fve.network.model

import com.aowen.fve.data.model.VehiclePageDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network representation of the response that wraps a list of [NetworkVehicle] when fetched from /vehicle.
 */
@Serializable
data class NetworkVehiclesResponse(
    @SerialName("start_cursor")
    val startCursor: String,
    @SerialName("next_cursor")
    val nextCursor: String?,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("estimated_remaining_count")
    val estimatedRemainingCount: Int,
    @SerialName("filtered_by")
    val filteredBy: List<String>,
    @SerialName("sorted_by")
    val sortedBy: List<Map<String, String>>,
    val records: List<NetworkVehicle>,
)

fun NetworkVehiclesResponse.asVehicleListItem(): VehiclePageDetails =
    VehiclePageDetails(
        startCursor = startCursor,
        nextCursor = nextCursor,
        perPage = perPage,
        estimatedRemainingCount = estimatedRemainingCount,
        vehicles = records.map { it.asVehicleListItem() }
    )


