package com.example.marvelstudios


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvelstudios.data.CharacterRepository
import com.example.marvelstudios.checkNetworkAvailability
import com.example.marvelstudios.presentation.MainMenu
import com.example.marvelstudios.presentation.CardInfo

@Composable
fun Navigation(
    navController: NavHostController,
    characterRepository: CharacterRepository,
) {
    val context = LocalContext.current
    val isNetworkAvailable = remember { context.checkNetworkAvailability() }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainMenu(
                navController = navController,
                characterRepository = characterRepository,
                isNetworkAvailable = isNetworkAvailable
            )
        }
        composable("heroDetail/{id}/{url}/{name}/{description}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "Неизвестный ID"
            val url = backStackEntry.arguments?.getString("url") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: "Неизвестно"
            val description = backStackEntry.arguments?.getString("description") ?: "Описание отсутствует"

            CardInfo(
                url = url,
                name = name,
                description = description,
                onClose = { navController.popBackStack() }
            )
        }
    }
}