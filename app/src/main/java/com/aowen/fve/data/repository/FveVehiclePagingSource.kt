package com.aowen.fve.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveNetworkDatasource
import com.aowen.fve.network.FveResult
import com.aowen.fve.network.map
import com.aowen.fve.network.model.asVehicleListItem
import com.aowen.fve.ui.viewmodel.SortingMethod
import retrofit2.HttpException
import retrofit2.Response

class FveVehiclePagingSource(
    private val networkDatasource: FveNetworkDatasource,
    val sortingMethod: SortingMethod
): PagingSource<String, VehicleListItem>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, VehicleListItem> {
        val startCursor = params.key
        return try {
            val vehiclesRes = networkDatasource.getVehicles(startCursor, sortingMethod).map { it.asVehicleListItem() }
            when(vehiclesRes) {
                is FveResult.Success -> {
                    val vehiclesDetails = vehiclesRes.data
                    LoadResult.Page(
                        data = vehiclesDetails.vehicles,
                        prevKey = null,
                        nextKey = vehiclesDetails.nextCursor
                    )
                }
                is FveResult.Error -> {
                    when(vehiclesRes.error) {
                        is FveCommonError.UnknownError -> LoadResult.Error(Exception(vehiclesRes.error.message))
                        is FveCommonError.NetworkError -> LoadResult.Error(HttpException(Response.success(vehiclesRes.error.message)))
                    }
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, VehicleListItem>): String? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
        }
    }
}