package com.aowen.fve.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.aowen.fve.fakes.data.fakeVehicleDetails
import com.aowen.fve.fakes.viewmodel.FakeFveVehicleRepository
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveResult
import com.aowen.fve.utils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehicleDetailsScreenViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private var vehicleRepository = FakeFveVehicleRepository(
        vehicleDetailsResult = FveResult.Success(fakeVehicleDetails)
    )

    private var savedStateHandle = SavedStateHandle(
        mapOf(
            "id" to 1
        )

    )

    private lateinit var viewModel: VehicleDetailsScreenViewModel

    @Before
    fun setUp() {
        viewModel = VehicleDetailsScreenViewModel(
            savedStateHandle = savedStateHandle,
            vehicleRepository = vehicleRepository
        )
    }

    @Test
    fun `getVehicleDetails should update uiState with vehicle details`() = runTest {
        val actual = viewModel.uiState.value
        assertTrue(actual is VehicleDetailsUiState.Success)
        assertEquals((actual as VehicleDetailsUiState.Success).data, fakeVehicleDetails)
    }

    @Test
    fun `getVehicleDetails should update uiState with error message if network error`() = runTest {
        vehicleRepository = FakeFveVehicleRepository(
            vehicleDetailsResult = FveResult.Error(FveCommonError.NetworkError(404, "Not Found"))
        )
        viewModel = VehicleDetailsScreenViewModel(
            savedStateHandle = savedStateHandle,
            vehicleRepository = vehicleRepository
        )
        val actual = viewModel.uiState.value
        assertTrue(actual is VehicleDetailsUiState.Error)
        assertEquals((actual as VehicleDetailsUiState.Error).message, "Network Error: 404 Not Found")
    }

    @Test
    fun `getVehicleDetails should update uiState with error message if unknown error`() = runTest {
        vehicleRepository = FakeFveVehicleRepository(
            vehicleDetailsResult = FveResult.Error(FveCommonError.UnknownError("Something went wrong"))
        )
        viewModel = VehicleDetailsScreenViewModel(
            savedStateHandle = savedStateHandle,
            vehicleRepository = vehicleRepository
        )
        val actual = viewModel.uiState.value
        assertTrue(actual is VehicleDetailsUiState.Error)
        assertEquals((actual as VehicleDetailsUiState.Error).message, "Unknown Error: Something went wrong")
    }
}