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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.maxidev.boxsplash.presentation.theme.BoxsplashTheme

@Composable
fun TopicCardComposable(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    onClick: () -> Unit
) {
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
                    .fillMaxWidth()
                    .aspectRatio(10f / 4f)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = title,
                    style = textStyle,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TopicCardComposablePreview() {
    BoxsplashTheme {
        TopicCardComposable(
            title = "Lorem impsum.",
            image = "image",
            onClick = {}
        )
    }
}