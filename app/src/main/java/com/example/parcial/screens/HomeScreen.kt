package com.example.parcial.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.parcial.components.DrinkCard
import com.example.parcial.navigation.Routes
import com.example.parcial.viewmodel.DrinkViewModel
import com.example.parcial.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: DrinkViewModel
) {

    val state by
    viewModel.drinksState.collectAsState()

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Bebidas")
                },

                actions = {

                    Button(
                        onClick = {
                            navController.navigate(
                                Routes.FAVORITES
                            )
                        }
                    ) {
                        Text("Favoritos")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            when (state) {

                is UiState.Loading -> {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.align(
                                Alignment.Center
                            )
                    )
                }

                is UiState.Error -> {

                    Text(
                        text =
                            (state as UiState.Error)
                                .message,

                        modifier =
                            Modifier.align(
                                Alignment.Center
                            )
                    )
                }

                is UiState.Success -> {

                    val drinks =
                        (state as UiState.Success)
                            .data

                    LazyColumn {

                        items(drinks) { drink ->

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
        }
    }
}