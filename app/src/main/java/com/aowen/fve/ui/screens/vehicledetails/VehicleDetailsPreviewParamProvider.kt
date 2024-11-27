package com.aowen.fve.ui.screens.vehicledetails

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.aowen.fve.data.model.DriverDetails
import com.aowen.fve.data.model.VehicleDetails
import com.aowen.fve.data.model.VehicleImage
import com.aowen.fve.data.model.VehicleType
import com.aowen.fve.ui.viewmodel.VehicleDetailsUiState

data class VehicleDetailsPreviewState(
    val vehicleDetailsUiState: VehicleDetailsUiState = VehicleDetailsUiState.Success(
        data = VehicleDetails(
            id = 1,
            name = "Vehicle Name",
            imageUrl = VehicleImage(
                default = "https://via.placeholder.com/150",
                large = "https://via.placeholder.com/150"
            ),
            type = VehicleType.PICKUP_TRUCK,
            year = 2022,
            make = "Toyota",
            model = "Tacoma",
            vin = "1234567890",
            licensePlate = "ABC123",
            primaryMeterReadout = "34524 mi",
            driverDetails = DriverDetails(
                id = 1,
                fullName = "John Doe",
                defaultImageUrl = "https://via.placeholder.com/150"
            )
        )
    )
)

class VehicleDetailsPreviewParamProvider : PreviewParameterProvider<VehicleDetailsPreviewState> {

    override val values: Sequence<VehicleDetailsPreviewState>
        get() = sequenceOf(
            VehicleDetailsPreviewState(),
            VehicleDetailsPreviewState(
                vehicleDetailsUiState = VehicleDetailsUiState.Loading
            ),
            VehicleDetailsPreviewState(
                vehicleDetailsUiState = VehicleDetailsUiState.Error("Error message")
            )
        )
}