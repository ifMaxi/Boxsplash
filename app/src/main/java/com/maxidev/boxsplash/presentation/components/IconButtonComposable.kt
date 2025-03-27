package com.maxidev.boxsplash.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.maxidev.boxsplash.R
import com.maxidev.boxsplash.presentation.theme.BoxsplashTheme

@Composable
fun IconButtonComposable(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun IconButtonComposablePreview() {
    BoxsplashTheme {
        IconButtonComposable(
            icon = Icons.Default.Preview,
            contentDescription = R.string.search,
            onClick = {}
        )
    }
}