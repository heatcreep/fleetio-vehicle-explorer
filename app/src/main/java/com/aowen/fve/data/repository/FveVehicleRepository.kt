package com.aowen.fve.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aowen.fve.data.model.VehicleDetails
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveNetworkDatasource
import com.aowen.fve.network.FveResult
import com.aowen.fve.network.map
import com.aowen.fve.network.model.asVehicleDetails
import com.aowen.fve.ui.viewmodel.SortingMethod
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FveVehicleRepository @Inject constructor(
    private val networkDatasource: FveNetworkDatasource
) : VehicleRepository {

    override fun getVehicles(sortingMethod: SortingMethod):
            Flow<PagingData<VehicleListItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FveVehiclePagingSource(networkDatasource, sortingMethod)
            }
        ).flow

    override suspend fun getVehicleDetails(id: Int): FveResult<VehicleDetails, FveCommonError> =
        coroutineScope {
            try {
                val vehicleDetailsRes = async { networkDatasource.getVehicleDetails(id) }
                vehicleDetailsRes.await().map {
                    it.asVehicleDetails()
                }
            } catch (e: Exception) {
                FveResult.Error(FveCommonError.UnknownError(e.localizedMessage))
            }
        }
}