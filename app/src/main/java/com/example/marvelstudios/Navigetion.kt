package com.example.marvelstudios


import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.marvelstudios.presentation.CardInfo
import com.example.marvelstudios.presentation.MainMenu

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainMenu") {
        composable("mainMenu") {
            MainMenu(navController)
        }
        composable(
            "heroInfo/{name}/{heroImageUrl}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("heroImageUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = Uri.decode(backStackEntry.arguments?.getString("name")) ?: "Unknown"
            val heroImageUrl = Uri.decode(backStackEntry.arguments?.getString("heroImageUrl")) ?: ""
            val description = when (name) {
                "Deadpool" -> stringResource(id = R.string.hero_description_deadpool)
                "Iron Man" -> stringResource(id = R.string.hero_description_iron_man)
                "Spider-Man" -> stringResource(id = R.string.hero_description_spider_man)
                else -> stringResource(id = R.string.hero_description_spider_man)
            }
            CardInfo(name, description, heroImageUrl, onClick = { navController.popBackStack() })
        }
    }
}
