package com.aowen.fve.network.model

import com.aowen.fve.data.model.DriverDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkDriver(
    val id: Int?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("default_image_url")
    val defaultImageUrl: String?,
)

fun NetworkDriver.asDriverDetails(): DriverDetails =
    DriverDetails(
        id = id,
        firstName = firstName,
        lastName = lastName,
        fullName = fullName,
        defaultImageUrl = defaultImageUrl,
    )

