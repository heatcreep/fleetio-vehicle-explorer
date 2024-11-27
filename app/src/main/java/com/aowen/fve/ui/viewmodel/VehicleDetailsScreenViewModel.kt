package com.aowen.fve.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aowen.fve.data.model.VehicleDetails
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.repository.VehicleRepository
import com.aowen.fve.network.FveCommonError
import com.aowen.fve.network.FveResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class VehicleDetailsUiState {
    data object Loading : VehicleDetailsUiState()
    data class Error(val message: String) : VehicleDetailsUiState()
    data class Success(val data: VehicleDetails) : VehicleDetailsUiState()
}

@HiltViewModel
class VehicleDetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val vehicleRepository: VehicleRepository
): ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState: MutableStateFlow<VehicleDetailsUiState> = MutableStateFlow(VehicleDetailsUiState.Loading)
    val uiState: StateFlow<VehicleDetailsUiState> = _uiState

    init {
        getVehicleDetails()
    }

    fun getVehicleDetails() {
        viewModelScope.launch {
            vehicleRepository.getVehicleDetails(id).let { result ->
                when (result) {
                    is FveResult.Success -> _uiState.value = VehicleDetailsUiState.Success(result.data)
                    is FveResult.Error -> {
                        when(result.error) {
                            is FveCommonError.NetworkError -> _uiState.value = VehicleDetailsUiState.Error("Network Error: ${result.error.code} ${result.error.message ?: "No message provided"}")
                            is FveCommonError.UnknownError -> _uiState.value = VehicleDetailsUiState.Error("Unknown Error: ${result.error.message ?: "No message provided"}")
                        }
                    }
                }
            }
        }
    }


}
