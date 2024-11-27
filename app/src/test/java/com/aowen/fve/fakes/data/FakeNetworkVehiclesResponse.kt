package com.aowen.fve.fakes.data

import com.aowen.fve.network.model.NetworkVehicle
import com.aowen.fve.network.model.NetworkVehiclesResponse

val fakeNetworkVehiclesResponse = NetworkVehiclesResponse(
    startCursor = "abc123",
    nextCursor = "def456",
    perPage = 20,
    estimatedRemainingCount = 100,
    filteredBy = listOf("status=active", "type=sedan"),
    sortedBy = listOf(
        mapOf("field" to "name", "order" to "asc"),
        mapOf("field" to "year", "order" to "desc")
    ),
    records = listOf(
        NetworkVehicle(
            id = 1,
            name = "Toyota Camry",
            make = "Toyota",
            model = "Camry",
            licensePlate = "ABC123",
            vehicleTypeId = 1,
            vehicleTypeName = "Sedan",
            year = 2020,
            color = "Blue",
            defaultImageUrlSmall = "https://example.com/camry_small.jpg",
            defaultImageUrlLarge = "https://example.com/camry_large.jpg",
            primaryMeterUnit = "miles",
            primaryMeterValue = "25000",
            secondaryMeterUnit = "km",
            secondaryMeterValue = "40000",
            vehicleStatusId = 1,
            vin = "1HGCM82633A123456"
        ),
        NetworkVehicle(
            id = 2,
            name = "Honda Civic",
            make = "Honda",
            model = "Civic",
            licensePlate = "XYZ789",
            vehicleTypeId = 1,
            vehicleTypeName = "Sedan",
            year = 2018,
            color = "Red",
            defaultImageUrlSmall = "https://example.com/civic_small.jpg",
            defaultImageUrlLarge = "https://example.com/civic_large.jpg",
            primaryMeterUnit = "miles",
            primaryMeterValue = "42000",
            secondaryMeterUnit = null,
            secondaryMeterValue = "0",
            vehicleStatusId = 2,
            vin = "2HGEJ6526VH123456"
        )
    )
)
