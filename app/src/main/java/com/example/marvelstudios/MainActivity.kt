package com.example.marvelstudios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.marvelstudios.api.CharacterViewModel

class MainActivity : ComponentActivity() {

    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        characterViewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        setContent {
            MarvelApp(characterViewModel)
        }
    }
}

@Composable
fun MarvelApp(characterViewModel: CharacterViewModel) {
    val navController = rememberNavController()
    Navigation(
        navController = navController,
        characterViewModel = characterViewModel
    )
}