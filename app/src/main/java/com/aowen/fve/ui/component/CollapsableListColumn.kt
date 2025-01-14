package com.aowen.fve.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.map

@Composable
fun CollapsableListColumn(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    content: @Composable () -> Unit
) {
    val contentShown = remember { mutableStateOf(true) }

    var previousIndex by remember { mutableIntStateOf(listState.firstVisibleItemIndex) }
    var previousScrollOffset by remember { mutableIntStateOf(listState.firstVisibleItemScrollOffset) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index ->
                if (previousIndex != index) {
                    previousIndex > index
                } else {
                    previousScrollOffset >= listState.firstVisibleItemScrollOffset
                }.also {
                    previousIndex = index
                    previousScrollOffset = listState.firstVisibleItemScrollOffset
                }
            }
            .collect { isScrollingUp ->
                contentShown.value = isScrollingUp
            }
    }

    Column {
        AnimatedVisibility(visible = contentShown.value) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
            ) {
                content()
            }
        }
    }
}