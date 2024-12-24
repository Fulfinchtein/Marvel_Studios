package com.example.marvelstudios.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.marvelstudios.R

@Composable
fun CardInfo(
    name: String,
    description: String,
    heroImageUrl: String,
    onClick: () -> Unit
) {
    Box {
        AsyncImage(
            model = heroImageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column {
            IconButton(
                onClick = onClick,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = name,
                color = White,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 39.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp, bottom = 50.dp)
            )

            Text(
                text = description,
                color = White,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp, bottom = 20.dp)
            )
        }
    }
}