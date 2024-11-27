package com.aowen.fve.fakes.data

import com.aowen.fve.network.model.NetworkDriver
import com.aowen.fve.network.model.NetworkVehicleDetails

val fakeNetworkVehicleDetailsResponse = NetworkVehicleDetails(
    id = 1,
    name = "Toyota Camry",
    make = "Toyota",
    model = "Camry",
    licensePlate = "ABC123",
    vehicleTypeId = 1,
    vehicleTypeName = "Sedan",
    year = 2020,
    color = "Blue",
    defaultImageUrl = "https://example.com/camry_small.jpg",
    defaultImageUrlLarge = "https://example.com/camry_large.jpg",
    primaryMeterUnit = "miles",
    primaryMeterValue = "25000",
    secondaryMeterUnit = "km",
    secondaryMeterValue = "40000",
    vehicleStatusId = 1,
    vin = "1HGCM82633A123456",
    driver = NetworkDriver(
        id = 1,
        firstName = "John",
        lastName = "Smith",
        fullName = "John Smith",
        defaultImageUrl = "https://example.com/driver_small.jpg",
    )
)