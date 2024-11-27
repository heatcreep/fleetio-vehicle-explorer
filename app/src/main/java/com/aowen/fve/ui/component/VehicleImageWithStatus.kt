package com.aowen.fve.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.aowen.fve.data.model.VehicleStatus

@Composable
fun VehicleImageWithStatus(
    imageUrl: String,
    status: VehicleStatus,
    contentScale: ContentScale = ContentScale.Crop
) {
    Box {
        AsyncImage(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            model = imageUrl,
            contentScale = contentScale,
            contentDescription = "Vehicle Image",
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(status.badgeColor)
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = status.statusName,
                )
            }
        }
    }
}