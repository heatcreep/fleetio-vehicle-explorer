package com.aowen.fve.network.retrofit

import androidx.compose.ui.util.trace
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveNetworkDatasource
import com.aowen.fve.network.FveResult
import com.aowen.fve.network.handleApi
import com.aowen.fve.network.model.NetworkVehicleDetails
import com.aowen.fve.network.model.NetworkVehiclesResponse
import com.aowen.fve.ui.viewmodel.SortingMethod
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitFleetioNetworkApi {

    @GET("/api/v1/vehicles")
    suspend fun getVehicles(
        @Query("start_cursor") startCursor: String?,
        @QueryMap sortingMethod: Map<String,String>
    ): Response<NetworkVehiclesResponse>

    @GET("/api/v1/vehicles/{id}")
    suspend fun getVehicleDetails(
        @Path("id") id: Int,
    ): Response<NetworkVehicleDetails>
}

private const val FLEETIO_BASE_URL = "https://secure.fleetio.com"

@Singleton
class RetrofitFleetioNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : FveNetworkDatasource {
    private val networkApi = trace("RetrofitFleetioNetwork") {
        Retrofit.Builder()
            .baseUrl(FLEETIO_BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitFleetioNetworkApi::class.java)
    }

    override suspend fun getVehicles(startCursor: String?, sortingMethod: SortingMethod): FveResult<NetworkVehiclesResponse, FveCommonError> =
        handleApi {
            networkApi.getVehicles(startCursor, sortingMethod.filterParams)
        }

    override suspend fun getVehicleDetails(id: Int): FveResult<NetworkVehicleDetails, FveCommonError> =
        handleApi {
            networkApi.getVehicleDetails(id)
        }

}