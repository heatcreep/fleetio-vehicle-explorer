package com.aowen.fve.fakes

import com.aowen.fve.fakes.data.fakeNetworkVehicleDetailsResponse
import com.aowen.fve.fakes.data.fakeNetworkVehiclesResponse
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveNetworkDatasource
import com.aowen.fve.network.FveResult
import com.aowen.fve.network.model.NetworkVehicle
import com.aowen.fve.network.model.NetworkVehicleDetails
import com.aowen.fve.network.model.NetworkVehiclesResponse
import com.aowen.fve.ui.viewmodel.SortingMethod

class FakeFveNetworkDataSource(
    private val fakeVehicleDetailsResult: FveResult<NetworkVehicleDetails, FveCommonError> = FveResult.Success(
        fakeNetworkVehicleDetailsResponse
    ),
    private val fakeVehiclesResult: FveResult<NetworkVehiclesResponse, FveCommonError> = FveResult.Success(
        fakeNetworkVehiclesResponse
    )
) : FveNetworkDatasource {
    override suspend fun getVehicles(
        startCursor: String?,
        sortingMethod: SortingMethod
    ): FveResult<NetworkVehiclesResponse, FveCommonError> =
        fakeVehiclesResult

    override suspend fun getVehicleDetails(id: Int): FveResult<NetworkVehicleDetails, FveCommonError> =
        fakeVehicleDetailsResult
}