package com.aowen.fve.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.model.VehicleStatus
import com.aowen.fve.data.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class SortingMethod(val label: String, val filterParams: Map<String, String>) {
    NAME_ASC(label = "Name (Ascending)", mapOf("sort[name]" to "asc")),
    NAME_DESC(label = "Name (Descending)" , mapOf("sort[name]" to "desc")),
    CREATED_AT_ASC(label = "Creation Date (Ascending)", mapOf("sort[created_at]" to "asc")),
    CREATED_AT_DESC(label = "Creation Date (Descending)", mapOf("sort[created_at]" to "desc")),
    UPDATED_AT_ASC(label = "Last Updated (Ascending)", mapOf("sort[updated_at]" to "asc")),
    UPDATED_AT_DESC(label = "Last Updated (Descending)", mapOf("sort[updated_at]" to "desc")),
    ID_ASC(label = "ID (Ascending)", mapOf("sort[id]" to "asc")),
    ID_DESC(label = "ID (Descending)", mapOf("sort[id]" to "desc"))
}

data class VehiclesScreenUiState(
    val typeFilters: List<Int> = emptyList(),
    val statusFilters: List<VehicleStatus> = emptyList(),
    val sortingMethod: SortingMethod = SortingMethod.ID_DESC
)

@HiltViewModel
class VehiclesScreenViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<VehiclesScreenUiState> =
        MutableStateFlow(VehiclesScreenUiState())
    val uiState: StateFlow<VehiclesScreenUiState> = _uiState

    fun handleTypeFilter(type: Int) {
        _uiState.update { currentState ->
            val newTypeFilters = if (currentState.typeFilters.contains(type)) {
                currentState.typeFilters - type
            } else {
                currentState.typeFilters + type
            }
            currentState.copy(typeFilters = newTypeFilters)
        }
    }

    fun handleStatusFilter(status: VehicleStatus) {
        _uiState.update { currentState ->
            val newStatusFilters = if (currentState.statusFilters.contains(status)) {
                currentState.statusFilters - status
            } else {
                currentState.statusFilters + status
            }
            currentState.copy(statusFilters = newStatusFilters)
        }
    }

    fun handleSortingMethod(sortingMethod: SortingMethod) {
        _uiState.update { currentState ->
            currentState.copy(sortingMethod = sortingMethod)
        }
    }

    fun getFilteredVehicles(): Flow<PagingData<VehicleListItem>> =
        vehicleRepository.getVehicles(uiState.value.sortingMethod).map { vehicle ->
            vehicle.filter { it.filterByType() && it.filterByStatus() }
        }.cachedIn(viewModelScope)

    private fun VehicleListItem.filterByType(): Boolean =
        if (uiState.value.typeFilters.isEmpty()) true else {
            uiState.value.typeFilters.contains(this.type.id)
        }

    private fun VehicleListItem.filterByStatus(): Boolean =
        if (uiState.value.statusFilters.isEmpty()) true else {
            uiState.value.statusFilters.contains(this.status)
        }
}