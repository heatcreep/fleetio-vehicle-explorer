package com.aowen.fve.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Modifier that fills the width of its parent, even
 * if the parent has padding.
 */
fun Modifier.fillWidthOfParent(parentPadding: Dp = 16.dp) = this.then(
    layout { measurable, constraints ->
        // This is to force layout to go beyond the borders of its parent
        val placeable = measurable.measure(
            constraints.copy(
                maxWidth = constraints.maxWidth + 2 * parentPadding.roundToPx(),
            ),
        )
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    },
)