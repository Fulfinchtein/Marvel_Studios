package com.example.marvelstudios


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.marvelstudios.api.CharacterViewModel
import com.example.marvelstudios.presentation.CardInfo
import com.example.marvelstudios.presentation.MainMenu

@Composable
fun Navigation(
    navController: NavHostController,
    characterViewModel: CharacterViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainMenu(navController, characterViewModel)
        }

        composable(
            route = "heroDetail/{characterId}/{imageUrl}/{characterName}/{characterDescription}",
            arguments = listOf(
                navArgument("characterId") { type = NavType.IntType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("characterName") { type = NavType.StringType },
                navArgument("characterDescription") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: -1

            CardInfo(
                navController = navController,
                characterViewModel = characterViewModel,
                characterId = characterId,
                onClick = { navController.popBackStack() }
            )
        }
    }
}
