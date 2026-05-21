package com.example.parcial.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.parcial.components.DrinkCard
import com.example.parcial.navigation.Routes
import com.example.parcial.viewmodel.DrinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: DrinkViewModel
) {

    val favorites =
        viewModel.getFavorites()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Favoritos")
                },

                navigationIcon = {

                    Button(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {

            items(favorites) { drink ->

                DrinkCard(
                    drink = drink
                ) {

                    navController.navigate(
                        "${Routes.DETAIL}/${drink.id}"
                    )
                }
            }
        }
    }
}