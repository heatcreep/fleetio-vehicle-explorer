package com.aowen.fve.fakes.data

import com.aowen.fve.data.model.VehicleImage
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.model.VehicleStatus
import com.aowen.fve.data.model.VehicleType

val fakeVehicleListItem = VehicleListItem(
    id = 1,
    name = "Toyota Camry",
    make = "Toyota",
    model = "Camry",
    licensePlate = "ABC123",
    year = 2020,
    color = "Blue",
    type = VehicleType.CAR,
    imageUrl = VehicleImage(
        default = "https://example.com/camry_small.jpg",
        large = "https://example.com/camry_large.jpg"),
    status = VehicleStatus.ACTIVE,
    primaryMeterReadout = "25000",
    secondaryMeterReadout = "40000",
    vin = "1HGCM82633A123456",
)
