package com.aowen.fve.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.aowen.fve.ui.theme.FleetioVehicleExplorerTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FakeImagePreviewProvider(
    content: @Composable () -> Unit
) {
    val previewHandler = AsyncImagePreviewHandler {
        FakeImage(color = 0xFF212121.toInt())
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        content()
    }

}