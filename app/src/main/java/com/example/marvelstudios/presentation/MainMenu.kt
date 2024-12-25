package com.example.marvelstudios.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.marvelstudios.R
import com.example.marvelstudios.api.CharacterViewModel
import com.example.marvelstudios.api.CharacterViewModelFactory
import com.example.marvelstudios.data.CharacterRepository
import com.example.marvelstudios.presentation.components.CardMenu

@Composable
fun MainMenu(
    navController: NavController,
    characterRepository: CharacterRepository,
    isNetworkAvailable: Boolean
) {
    val characterViewModel: CharacterViewModel = viewModel(
        factory = CharacterViewModelFactory(characterRepository)
    )

    val characters by characterViewModel.characters.collectAsState()
    val errorMessage by characterViewModel.errorMessage.collectAsState()
    val isLoading by characterViewModel.isLoading.collectAsState()

    LaunchedEffect(isNetworkAvailable) {
        characterViewModel.getCharacters(isNetworkAvailable)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background__1_),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(1.2f)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.marvel_studios),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Choose your hero",
                color = White,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(50.dp))

            if (characters.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(characters) { character ->
                        CardMenu(
                            name = character.name,
                            imageUrl = character.imageUrl,
                            onClick = {
                                navController.navigate(
                                    "heroDetail/${character.id}/${Uri.encode(character.imageUrl)}/" +
                                            "${Uri.encode(character.name)}/${Uri.encode(character.description ?: "")}"
                                )
                            },
                            modifier = Modifier.padding(8.dp) // Дополнительный отступ
                        )
                    }
                }
            } else {
                Text(
                    text = "No characters found",
                    color = White,
                    style = MaterialTheme.typography.bodyMedium,

                    )
            }
        }
    }

}
