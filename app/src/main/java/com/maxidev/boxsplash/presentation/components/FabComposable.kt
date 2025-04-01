package com.maxidev.boxsplash.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SmallFabComposable(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    SmallFloatingActionButton(
        modifier = modifier,
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescription)
        )
    }
}