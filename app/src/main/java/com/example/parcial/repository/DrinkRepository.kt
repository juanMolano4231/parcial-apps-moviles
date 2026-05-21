package com.example.parcial.repository

import android.content.Context
import com.example.parcial.model.Drink
import com.example.parcial.network.RetrofitInstance
import com.example.parcial.storage.LocalStorage

class DrinkRepository(context: Context) {

    private val storage = LocalStorage(context)

    suspend fun getDrinks(): List<Drink> {
        return try {
            val response = RetrofitInstance.api.getDrinks()
            val drinks = response.drinks ?: emptyList()

            storage.saveCachedDrinks(drinks)
            drinks
        } catch (e: Exception) {
            e.printStackTrace()
            storage.getCachedDrinks() ?: emptyList()
        }
    }

    suspend fun getDrinkById(id: String): Drink? {

        return try {

            RetrofitInstance.api
                .getDrinkById(id)
                .drinks?.firstOrNull()

        } catch (e: Exception) {

            storage.getCachedDrinks()
                .find { it.id == id }
        }
    }

    fun getFavorites(): MutableList<Drink> {
        return storage.getFavorites()
    }

    fun toggleFavorite(drink: Drink) {

        val favorites = storage.getFavorites()

        val exists =
            favorites.any { it.id == drink.id }

        if (exists) {
            favorites.removeAll { it.id == drink.id }
        } else {
            favorites.add(drink)
        }

        storage.saveFavorites(favorites)
    }

    fun isFavorite(id: String): Boolean {

        return storage
            .getFavorites()
            .any { it.id == id }
    }
}