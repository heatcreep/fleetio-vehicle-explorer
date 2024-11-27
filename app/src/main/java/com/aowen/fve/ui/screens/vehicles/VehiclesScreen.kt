package com.aowen.fve.ui.screens.vehicles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aowen.fve.R
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.model.VehicleStatus
import com.aowen.fve.ui.component.CollapsableListColumn
import com.aowen.fve.ui.component.VehicleCard
import com.aowen.fve.ui.screens.vehicledetails.navigateToVehicleDetails
import com.aowen.fve.ui.screens.vehicles.bottomsheet.SortVehiclesBottomSheet
import com.aowen.fve.ui.screens.vehicles.bottomsheet.StatusFiltersBottomSheet
import com.aowen.fve.ui.screens.vehicles.bottomsheet.TypeFiltersBottomSheet
import com.aowen.fve.ui.viewmodel.SortingMethod
import com.aowen.fve.ui.viewmodel.VehiclesScreenUiState
import com.aowen.fve.ui.viewmodel.VehiclesScreenViewModel

@Composable
fun VehiclesRoute(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: VehiclesScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val vehicles = viewModel.getFilteredVehicles().collectAsLazyPagingItems()

    VehiclesScreen(
        modifier = modifier.fillMaxSize(),
        uiState = uiState,
        vehicles = vehicles,
        onChangeTypeFilter = viewModel::handleTypeFilter,
        onChangeSortingMethod = viewModel::handleSortingMethod,
        onChangeStatusFilter = viewModel::handleStatusFilter,
        navigateToVehicleDetails = navController::navigateToVehicleDetails
    )
}

@Composable
fun VehiclesScreen(
    modifier: Modifier = Modifier,
    uiState: VehiclesScreenUiState,
    vehicles: LazyPagingItems<VehicleListItem>,
    onChangeTypeFilter: (Int) -> Unit,
    onChangeSortingMethod: (SortingMethod) -> Unit,
    onChangeStatusFilter: (VehicleStatus) -> Unit,
    navigateToVehicleDetails: (Int) -> Unit
) {

    val scrollState = rememberLazyListState()
    var openTypeFiltersBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openSortingBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openStatusFilterBottomSheet by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        CollapsableListColumn(
            listState = scrollState
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    AssistChip(
                        onClick = { openSortingBottomSheet = true },
                        label = { Text(text = stringResource(R.string.chip_label_vehicle_filters_sort)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Sort,
                                contentDescription = stringResource(R.string.cd_vehicle_filters_sort_vehicles)
                            )
                        }
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AssistChip(
                            onClick = { openTypeFiltersBottomSheet = true },
                            label = {
                                Text(
                                    text = stringResource(
                                        R.string.chip_label_vehicle_filters_type,
                                        uiState.typeFilters.count()
                                    )
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.DirectionsCar,
                                    contentDescription = stringResource(R.string.cd_vehicle_filters_type)
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        VerticalDivider(modifier = Modifier.height(24.dp))
                    }
                }
                item {
                    AssistChip(
                        onClick = { openStatusFilterBottomSheet = true },
                        label = {
                            Text(
                                text = stringResource(
                                    R.string.chip_label_vehicle_filters_status,
                                    uiState.statusFilters.size
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Flag,
                                contentDescription = stringResource(R.string.cd_vehicle_filters_status)
                            )
                        }
                    )
                }
            }
        }


        if (openTypeFiltersBottomSheet) {
            TypeFiltersBottomSheet(
                typeFilters = uiState.typeFilters,
                onChangeTypeFilter = onChangeTypeFilter,
                onDismiss = { openTypeFiltersBottomSheet = false }
            )
        }
        if (openSortingBottomSheet) {
            SortVehiclesBottomSheet(
                sortingMethod = uiState.sortingMethod,
                onChangeSortingMethod = onChangeSortingMethod,
                onDismiss = { openSortingBottomSheet = false }
            )
        }
        if (openStatusFilterBottomSheet) {
            StatusFiltersBottomSheet(
                statusFilters = uiState.statusFilters,
                onChangeStatusFilter = onChangeStatusFilter,
                onDismiss = { openStatusFilterBottomSheet = false }
            )
        }
        when (vehicles.loadState.refresh) {
            // Loading state
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            // Error state
            is LoadState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_generic_message),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            else -> {
                // Success state
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(vehicles.itemCount) { index ->
                        // Display vehicle
                        vehicles[index]?.let { vehicle ->
                            VehicleCard(
                                modifier = Modifier.clickable {
                                    navigateToVehicleDetails(vehicle.id)
                                },
                                vehicle = vehicle
                            )
                        }
                    }
                }
            }
        }


    }
}