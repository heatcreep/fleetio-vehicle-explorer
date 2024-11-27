package com.aowen.fve.network

import com.aowen.fve.network.model.NetworkVehicle
import com.aowen.fve.network.model.NetworkVehicleDetails
import com.aowen.fve.network.model.NetworkVehiclesResponse
import com.aowen.fve.ui.viewmodel.SortingMethod

interface FveNetworkDatasource {

    suspend fun getVehicles(startCursor: String?, sortingMethod: SortingMethod): FveResult<NetworkVehiclesResponse, FveCommonError>

    suspend fun getVehicleDetails(id: Int): FveResult<NetworkVehicleDetails, FveCommonError>
}