package com.example.marvelstudios.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marvelstudios.R
import com.example.marvelstudios.api.CharacterViewModel
import com.example.marvelstudios.presentation.components.CardMenu
import com.example.marvelstudios.presentation.components.LoadingScreen
import com.example.marvelstudios.presentation.components.ErrorScreen

@Composable
fun MainMenu(
    navController: NavController,
    characterViewModel: CharacterViewModel
) {
    val characters by characterViewModel.characters.collectAsState()
    val errorMessage by characterViewModel.errorMessage.collectAsState()
    val isLoading by characterViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        val apiKey = "378b183c534f3cbbbc0e9afb1ea09414"
        val privateKey = "e6094c6085eaf20b7e32b7b07baab4e26f19ff4f"
        characterViewModel.getCharacters(apiKey, privateKey)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background__1_),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(1.2f)
        )

        if (isLoading) {
            LoadingScreen()
        } else if (errorMessage != null) {
            ErrorScreen(message = errorMessage ?: "Неизвестная ошибка", onRetry = {})
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                Image(
                    painter = painterResource(id = R.drawable.marvel_studios),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Choose your hero",
                    color = White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Spacer(modifier = Modifier.height(70.dp))

                val listState = rememberLazyListState()
                val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    state = listState,
                    flingBehavior = flingBehavior,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(start = 25.dp, end = 25.dp, bottom = 50.dp),
                ) {
                    items(characters) { character ->
                        val imageUrl = "${character.thumbnail.path}.${character.thumbnail.extension}".replace(
                            "http://",
                            "https://"
                        )
                        CardMenu(
                            name = character.name,
                            imageUrl = imageUrl,
                            onClick = {
                                navController.navigate(
                                    "heroDetail/${character.id}/${Uri.encode(character.thumbnail.path)}" +
                                            "/${Uri.encode(character.name)}/${Uri.encode(character.description ?: "")}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

