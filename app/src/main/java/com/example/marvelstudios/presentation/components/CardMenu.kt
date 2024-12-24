package com.example.marvelstudios.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.marvelstudios.ui.theme.White

@Composable
fun CardMenu(
    name: String,
    heroImageUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .shadow(10.dp, shape = MaterialTheme.shapes.medium)
    ) {
        Box {
            AsyncImage(
                model = heroImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = name,
                color = White,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 45.dp, bottom = 40.dp)
            )
        }
    }
}