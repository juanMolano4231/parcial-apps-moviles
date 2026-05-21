package com.example.parcial.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.parcial.screens.DetailScreen
import com.example.parcial.screens.FavoritesScreen
import com.example.parcial.screens.HomeScreen
import com.example.parcial.utils.NotificationHelper
import com.example.parcial.viewmodel.DrinkViewModel

@Composable
fun AppNavigation(
    viewModel: DrinkViewModel,
    notificationHelper: NotificationHelper
) {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {

            HomeScreen(
                navController,
                viewModel
            )
        }

        composable(
            "${Routes.DETAIL}/{drinkId}",

            arguments = listOf(
                navArgument("drinkId") {
                    type = NavType.StringType
                }
            )
        ) {

            val drinkId =
                it.arguments?.getString("drinkId")
                    ?: ""

            DetailScreen(
                navController,
                drinkId,
                viewModel,
                notificationHelper
            )
        }

        composable(Routes.FAVORITES) {

            FavoritesScreen(
                navController,
                viewModel
            )
        }
    }
}