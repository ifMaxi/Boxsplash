package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.maxidev.boxsplash.presentation.theme.BoxsplashTheme

@Composable
fun CollectionCardComposable(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    totalPhotos: Int,
    onClick: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val roundedCorner = 10.dp
    val textStyle = TextStyle().copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = MaterialTheme.colorScheme.outline,
            blurRadius = 3f
        )
    )
    
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClick
    ) {
        Box {
            AsyncImage(
                model = image,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f / 1.5f)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(bottomEnd = roundedCorner))
                    .background(backgroundColor)
            ) {
                Text(
                    text = "Total photos: $totalPhotos",
                    style = textStyle,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(topEnd = roundedCorner))
                    .background(backgroundColor)
            ) {
                Text(
                    text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CollectionCardComposablePreview() {
    BoxsplashTheme {
        CollectionCardComposable(
            title = "Lorem ipsum dolor sit amet.",
            image = "Image",
            totalPhotos = 38,
            onClick = {}
        )
    }
}
