package com.maxidev.boxsplash.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxidev.boxsplash.R
import com.maxidev.boxsplash.presentation.theme.BoxsplashTheme

@Composable
fun IconButtonComposable(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes contentDescription: Int,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(contentDescription),
                tint = tint
            )
        }
    }
}

@Composable
fun FilledIconButtonComposable(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FilledIconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(contentDescription)
            )
        }
    }
}

@Preview
@Composable
private fun IconButtonComposablePreview() {
    BoxsplashTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            IconButtonComposable(
                icon = Icons.Default.Preview,
                contentDescription = R.string.search,
                onClick = {}
            )
            FilledIconButtonComposable(
                icon = Icons.Default.Preview,
                contentDescription = R.string.search,
                onClick = {}
            )
        }
    }
}