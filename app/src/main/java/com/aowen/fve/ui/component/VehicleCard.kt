package com.aowen.fve.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.aowen.fve.R
import com.aowen.fve.data.model.VehicleListItem
import com.aowen.fve.data.model.VehicleImage
import com.aowen.fve.ui.theme.FleetioVehicleExplorerTheme
import com.aowen.fve.ui.utils.FakeImagePreviewProvider

@Composable
fun VehicleCard(
    modifier: Modifier = Modifier,
    vehicle: VehicleListItem,
) {
    val vehicleCompleteName = "${vehicle.year} ${vehicle.make} ${vehicle.model}"
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        VehicleImageWithStatus(
            imageUrl = vehicle.imageUrl.default,
            status = vehicle.status,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = vehicle.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = vehicle.combinedDescription,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        vehicle.licensePlate?.let { plateNumber ->
            Text(
                text = stringResource(R.string.label_vehicle_info_license_plate, plateNumber),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}


@Preview
@Composable
fun VehicleCardPreview() {

    FleetioVehicleExplorerTheme {
        FakeImagePreviewProvider {
            Surface {
                VehicleCard(
                    modifier = Modifier.padding(16.dp),
                    vehicle = VehicleListItem(
                        id = 1,
                        name = "Vehicle 1",
                        imageUrl = VehicleImage(
                            default = "https://www.example.com/image.jpg",
                            large = "https://www.example.com/image.jpg"
                        ),
                        make = "Toyota",
                        model = "Corolla",
                        year = 2022,
                        color = "Red"
                    )
                )
            }
        }
    }
}