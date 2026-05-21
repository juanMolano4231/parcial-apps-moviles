package com.example.parcial.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial.model.Drink
import com.example.parcial.repository.DrinkRepository
import com.example.parcial.utils.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DrinkViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        DrinkRepository(application)

    private val notificationHelper =
        NotificationHelper(application)

    private val _drinksState =
        MutableStateFlow<UiState<List<Drink>>>(
            UiState.Loading
        )

    val drinksState:
            StateFlow<UiState<List<Drink>>> =
        _drinksState

    private val _detailState =
        MutableStateFlow<UiState<Drink>>(
            UiState.Loading
        )

    val detailState:
            StateFlow<UiState<Drink>> =
        _detailState

    init {
        loadDrinks()
    }

    fun loadDrinks() {
        viewModelScope.launch {
            _drinksState.value = UiState.Loading
            try {
                val drinks = repository.getDrinks()
                _drinksState.value = UiState.Success(drinks)
                notificationHelper.showNotification(
                    title = "Descarga completada",
                    message = "Se cargaron ${drinks.size} bebidas",
                    id = 1
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _drinksState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadDrinkById(id: String) {

        viewModelScope.launch {

            _detailState.value =
                UiState.Loading

            try {

                val drink =
                    repository.getDrinkById(id)

                if (drink != null) {

                    _detailState.value =
                        UiState.Success(drink)

                } else {

                    _detailState.value =
                        UiState.Error("No encontrado")
                }

            } catch (e: Exception) {

                e.printStackTrace()

                _detailState.value =
                    UiState.Error(
                        e.message ?: "Error cargando detalle"
                    )
            }
        }
    }

    fun toggleFavorite(drink: Drink) {

        repository.toggleFavorite(drink)

        notificationHelper.showNotification(
            title = "Favorito agregado",
            message = "${drink.name} fue agregado",
            id = 2
        )
    }

    fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }

    fun getFavorites(): List<Drink> {
        return repository.getFavorites()
    }
}