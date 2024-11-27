package com.aowen.fve.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.aowen.fve.fakes.FakeFveNetworkDataSource
import com.aowen.fve.fakes.data.fakeNetworkVehicleDetailsResponse
import com.aowen.fve.fakes.data.fakeNetworkVehiclesResponse
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveResult
import com.aowen.fve.network.model.asVehicleDetails
import com.aowen.fve.network.model.asVehicleListItem
import com.aowen.fve.ui.viewmodel.SortingMethod
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FveVehicleRepositoryTest {

    private var fakeNetworkDatasource = FakeFveNetworkDataSource()
    private var repository = FveVehicleRepository(fakeNetworkDatasource)

    @Test
    fun `getVehicles should return vehicles`() = runTest {

        val fakeNetworkDatasource = FakeFveNetworkDataSource().apply {
            getVehicles(null, SortingMethod.NAME_ASC)
        }

        val pagingSource = FveVehiclePagingSource(
            fakeNetworkDatasource,
            sortingMethod = SortingMethod.NAME_ASC
        )

        val pager = TestPager(PagingConfig(pageSize = 2), pagingSource)

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(result.data, fakeNetworkVehiclesResponse.asVehicleListItem().vehicles)
    }

    @Test
    fun `getVehicleDetails should return vehicle details`() = runTest {

        val result = repository.getVehicleDetails(1) as FveResult.Success

        assertEquals(result.data, fakeNetworkVehicleDetailsResponse.asVehicleDetails())
    }

    @Test
    fun `getVehicleDetails should return error when network error`() = runTest {

        val fakeNetworkDatasource = FakeFveNetworkDataSource(
            fakeVehicleDetailsResult = FveResult.Error(FveCommonError.UnknownError("Network error"))
        )

        val repository = FveVehicleRepository(fakeNetworkDatasource)

        val result = repository.getVehicleDetails(1) as FveResult.Error

        assertEquals(result.error, FveCommonError.UnknownError("Network error"))
    }
}