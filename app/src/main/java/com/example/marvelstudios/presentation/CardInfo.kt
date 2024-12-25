package com.example.marvelstudios.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CardInfo(
    url: String,
    name: String,
    description: String,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = { onClose() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.W900,
                fontSize = 45.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 15.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(4f, 4f),
                        blurRadius = 16f
                    )
                )
            )

            Text(
                text = description,
                fontWeight = FontWeight.W700,
                fontSize = 28.sp,
                color = Color.White,
                lineHeight = 30.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 16f
                    )
                )
            )
        }
    }
}
