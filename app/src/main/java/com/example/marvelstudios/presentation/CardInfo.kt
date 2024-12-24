package com.example.marvelstudios.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvelstudios.R
import com.example.marvelstudios.api.CharacterViewModel
import com.example.marvelstudios.presentation.components.LoadingScreen

@Composable
fun CardInfo(
    navController: NavController,
    characterViewModel: CharacterViewModel,
    characterId: Int,
    onClick: () -> Unit
) {
    val characterDetails by characterViewModel.selectedCharacter.collectAsState()
    val isLoading by characterViewModel.isLoading.collectAsState()

    LaunchedEffect(characterId) {
        characterViewModel.getCharacterDetails(characterId)
    }

    when {
        isLoading -> {
            LoadingScreen()
        }
        characterDetails != null -> {
            val character = characterDetails!!
            Box {
                AsyncImage(
                    model = "${character.thumbnail.path}.${character.thumbnail.extension}".replace(
                        "http://",
                        "https://"
                    ),
                    contentDescription = "Character image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    onError = {
                        Log.e("CardInfo", "Error loading image: ${it.result.throwable.message}")
                    }
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
                        text = character.name,
                        color = White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 39.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 20.dp, bottom = 50.dp)
                    )

                    Text(
                        text = character.description ?: "No description available",
                        color = White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 20.dp, bottom = 20.dp)
                    )
                }

            }
        }
    }
}