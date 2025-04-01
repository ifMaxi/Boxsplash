package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ImageComposable(
    modifier: Modifier = Modifier,
    image: String,
    contentScale: ContentScale,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            onClick = onClick
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = contentScale,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    // Add blur effect when loading.
}