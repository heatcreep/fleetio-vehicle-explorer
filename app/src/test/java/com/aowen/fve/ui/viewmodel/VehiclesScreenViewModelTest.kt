package com.aowen.fve.ui.viewmodel

import com.aowen.fve.fakes.data.fakeVehicleDetails
import com.aowen.fve.fakes.data.fakeVehicleListItem
import com.aowen.fve.fakes.viewmodel.FakeFveVehicleRepository
import com.aowen.fve.network.FveResult
import com.aowen.fve.utils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehiclesScreenViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private var vehicleRepository = FakeFveVehicleRepository(
        vehicleDetailsResult = FveResult.Success(fakeVehicleDetails)
    )

    private lateinit var viewModel: VehiclesScreenViewModel

    @Before
    fun setUp() {
        viewModel = VehiclesScreenViewModel(
            vehicleRepository = vehicleRepository
        )
    }

    @Test
    fun `handleTypeFilter should update uiState with int type filter`() = runTest {

        viewModel.handleTypeFilter(1)
        val actual = viewModel.uiState.value
        val expected = VehiclesScreenUiState(
            typeFilters = listOf(1),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `handleTypeFilter should update uiState if type filter already exists`() = runTest {

        viewModel.handleTypeFilter(1)
        var actual = viewModel.uiState.value
        var expected = VehiclesScreenUiState(
            typeFilters = listOf(1),
        )
        assertEquals(expected, actual)
        viewModel.handleTypeFilter(1)
        actual = viewModel.uiState.value
        expected = VehiclesScreenUiState(
            typeFilters = emptyList(),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `handleStatusFilter should update uiState with VehicleStatus filter`() = runTest {

        viewModel.handleStatusFilter(fakeVehicleListItem.status)
        val actual = viewModel.uiState.value
        val expected = VehiclesScreenUiState(
            statusFilters = listOf(fakeVehicleListItem.status),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `handleStatusFilter should update uiState if VehicleStatus filter already exists`() =
        runTest {

            viewModel.handleStatusFilter(fakeVehicleListItem.status)
            var actual = viewModel.uiState.value
            var expected = VehiclesScreenUiState(
                statusFilters = listOf(fakeVehicleListItem.status),
            )
            assertEquals(expected, actual)
            viewModel.handleStatusFilter(fakeVehicleListItem.status)
            actual = viewModel.uiState.value
            expected = VehiclesScreenUiState(
                statusFilters = emptyList(),
            )
            assertEquals(expected, actual)
        }

    @Test
    fun `handleSortingMethod should update uiState with SortingMethod`() = runTest {

        viewModel.handleSortingMethod(SortingMethod.ID_DESC)
        val actual = viewModel.uiState.value
        val expected = VehiclesScreenUiState(
            sortingMethod = SortingMethod.ID_DESC,
        )
        assertEquals(expected, actual)
    }
}