package com.aowen.fve.data.model

enum class VehicleType(val id: Int, val typeName: String) {
    CAR(1677517, "Car"),
    PICKUP_TRUCK(1677524, "Pickup Truck"),
    SEMI_TRUCK(1677526, "Semi Truck"),
    FORKLIFT(1677518, "Forklift"),
    TRAILER(1677527, "Trailer"),
    VAN(1677528, "Van"),
    OTHER(9999999, "Other")
}