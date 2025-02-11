package com.mendelin.androidintegrator.designsystem

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*


@Composable
fun CardItem(painter: Painter, imageDescription: String?, text: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.25.dp, Color.DarkGray)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.End
        ) {
            Image(
                painter = painter,
                contentDescription = imageDescription,
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = text, fontSize = 13.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Button(onClick = onClick) {
                Text("Check it out".uppercase())
            }
        }
    }
}
