package com.maxidev.boxsplash.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComposable(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    shape: Shape = ButtonDefaults.shape,
    onClick: () -> Unit
) {
    val buttonDef = ButtonDefaults

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        elevation = buttonDef.buttonElevation(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text
        )
        Spacer(modifier = Modifier.width(buttonDef.IconSpacing))
        Text(
            text = text
        )
    }
}