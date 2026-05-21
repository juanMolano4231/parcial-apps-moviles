package com.example.parcial.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.parcial.model.Drink
import com.example.parcial.utils.NotificationHelper
import com.example.parcial.viewmodel.DrinkViewModel
import com.example.parcial.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    drinkId: String,
    viewModel: DrinkViewModel,
    notificationHelper: NotificationHelper
) {

    val state by
    viewModel.detailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDrinkById(drinkId)
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Detalle")
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

        when (state) {

            is UiState.Loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {

                Text(
                    text =
                        (state as UiState.Error)
                            .message
                )
            }

            is UiState.Success -> {

                val drink =
                    (state as UiState.Success<Drink>)
                        .data

                var favorite by remember {
                    mutableStateOf(
                        viewModel.isFavorite(drink.id)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    AsyncImage(
                        model = drink.image,
                        contentDescription = drink.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = drink.name,
                        style =
                            MaterialTheme
                                .typography
                                .headlineMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Categoría: ${drink.category}"
                    )

                    Text(
                        text =
                            "Alcohol: ${drink.alcoholic}"
                    )

                    Text(
                        text =
                            "Vaso: ${drink.glass}"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            drink.instructions ?: ""
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Row {

                        Text("Favorito")

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Switch(
                            checked = favorite,

                            onCheckedChange = {

                                favorite = it

                                viewModel.toggleFavorite(
                                    drink
                                )

                                notificationHelper
                                    .showNotification(
                                        "Favoritos",
                                        if (it)
                                            "Agregado a favoritos"
                                        else
                                            "Eliminado de favoritos"
                                    )
                            }
                        )
                    }
                }
            }
        }
    }
}