package com.aowen.fve.ui.screens.vehicledetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.GasMeter
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.sharp.GasMeter
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.aowen.fve.R
import com.aowen.fve.ui.component.VehicleImageWithStatus
import com.aowen.fve.ui.component.VehicleInfoRow
import com.aowen.fve.ui.theme.FleetioVehicleExplorerTheme
import com.aowen.fve.ui.utils.FakeImagePreviewProvider
import com.aowen.fve.ui.utils.fillWidthOfParent
import com.aowen.fve.ui.viewmodel.VehicleDetailsScreenViewModel
import com.aowen.fve.ui.viewmodel.VehicleDetailsUiState

@Composable
fun VehicleDetailsRoute(
    navController: NavController,
    viewModel: VehicleDetailsScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    VehicleDetailScreen(
        uiState = uiState,
        onRetry = {
            viewModel.getVehicleDetails()
        },
        onBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailScreen(
    uiState: VehicleDetailsUiState,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0, 0, 0, 0),
                title = {
                    Text(
                        text = stringResource(R.string.title_screen_vehicle_details),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.cd_icon_back)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        when (uiState) {
            is VehicleDetailsUiState.Success -> {
                val vehicle = uiState.data
                val driverDetails = vehicle.driverDetails
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                ) {
                    VehicleImageWithStatus(
                        imageUrl = vehicle.imageUrl.large,
                        status = vehicle.status
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = vehicle.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = vehicle.type.typeName,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.W200
                                )
                            )
                            VerticalDivider(modifier = Modifier.height(16.dp))
                            Text(
                                text = vehicle.combinedDescription,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.W200
                                )
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        VehicleInfoRow(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.GasMeter,
                                    contentDescription = stringResource(R.string.cd_icon_primary_meter_readout)
                                )
                            },
                            label = stringResource(R.string.label_vehicle_details_mileage),
                            value = vehicle.primaryMeterReadout
                        )
                        HorizontalDivider(
                            Modifier
                                .fillWidthOfParent()
                                .padding(vertical = 8.dp)
                        )
                        // Secondary meter readout
                        vehicle.secondaryMeterReadout?.let { readout ->
                            VehicleInfoRow(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Sharp.GasMeter,
                                        contentDescription = stringResource(R.string.cd_icon_secondary_meter_readout)
                                    )
                                },
                                label = stringResource(R.string.label_vehicle_details_secondary_meter),
                                value = readout
                            )
                            HorizontalDivider(
                                Modifier
                                    .fillWidthOfParent()
                                    .padding(vertical = 8.dp)
                            )
                        }
                        // VIN/SN
                        VehicleInfoRow(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Numbers,
                                    contentDescription = stringResource(R.string.cd_icon_vin)
                                )
                            },
                            label = stringResource(R.string.label_vehicle_details_vin),
                            value = vehicle.vin
                        )
                        HorizontalDivider(
                            Modifier
                                .fillWidthOfParent()
                                .padding(vertical = 8.dp)
                        )
                        vehicle.licensePlate?.let { plate ->
                            VehicleInfoRow(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.DirectionsCar,
                                        contentDescription = "License Plate"
                                    )
                                },
                                label = stringResource(R.string.label_vehicle_details_license_plate),
                                value = plate
                            )
                            HorizontalDivider(
                                Modifier
                                    .fillWidthOfParent()
                                    .padding(vertical = 8.dp)
                            )
                        }
                        // Driver Info

                        if (driverDetails.fullName != null &&
                            driverDetails.defaultImageUrl != null
                        ) {
                            Text(
                                text = stringResource(R.string.label_vehicle_details_driver_info),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape),
                                        model = driverDetails.defaultImageUrl,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = stringResource(R.string.cd_image_driver_image),
                                    )
                                    Column {
                                        Text(
                                            text = driverDetails.fullName,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is VehicleDetailsUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.widthIn(max = 450.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.error_generic_message),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        ElevatedButton(
                            onClick = onRetry
                        ) {
                            Text(
                                text = stringResource(R.string.button_label_retry),
                            )
                        }
                    }
                }
            }

            is VehicleDetailsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun VehicleDetailScreenPreview(
    @PreviewParameter(VehicleDetailsPreviewParamProvider::class) previewState: VehicleDetailsPreviewState
) {
    FleetioVehicleExplorerTheme {
        FakeImagePreviewProvider {
            VehicleDetailScreen(
                uiState = previewState.vehicleDetailsUiState,
                onBack = {},
                onRetry = {}
            )
        }
    }
}
