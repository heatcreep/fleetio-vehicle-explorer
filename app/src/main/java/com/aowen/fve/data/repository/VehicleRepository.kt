package com.aowen.fve.data.repository

import androidx.paging.PagingData
import com.aowen.fve.data.model.VehicleDetails
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveResult
import com.aowen.fve.ui.viewmodel.SortingMethod
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {

    fun getVehicles(sortingMethod: SortingMethod): Flow<PagingData<VehicleListItem>>
    suspend fun getVehicleDetails(id: Int): FveResult<VehicleDetails, FveCommonError>
}