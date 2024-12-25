package com.example.marvelstudios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelstudios.api.RetrofitInstance
import com.example.marvelstudios.data.MarvelDatabase
import com.example.marvelstudios.data.CharacterRepository


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val marvelDatabase = MarvelDatabase.getInstance(this)
        val characterDao = marvelDatabase.characterDao()

        val characterRepository = CharacterRepository(
            apiService = RetrofitInstance.apiService,
            characterDao = characterDao
        )

        setContent {
            MarvelApp(
                characterRepository = characterRepository
            )
        }
    }
}

@Composable
fun MarvelApp(
    characterRepository: CharacterRepository,
) {
    val navController = rememberNavController()
    Navigation(
        navController = navController,
        characterRepository = characterRepository
    )
}

