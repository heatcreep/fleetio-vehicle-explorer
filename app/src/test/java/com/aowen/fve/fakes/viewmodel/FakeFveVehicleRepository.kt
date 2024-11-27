package com.aowen.fve.fakes.viewmodel

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aowen.fve.data.model.VehicleDetails
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.repository.VehicleRepository
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveResult
import com.aowen.fve.ui.viewmodel.SortingMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeFveVehicleRepository(
    private val vehicleDetailsResult: FveResult<VehicleDetails, FveCommonError>
) : VehicleRepository {
    override fun getVehicles(sortingMethod: SortingMethod): Flow<PagingData<VehicleListItem>> {
        TODO()
    }

    override suspend fun getVehicleDetails(id: Int): FveResult<VehicleDetails, FveCommonError> =
        vehicleDetailsResult
}