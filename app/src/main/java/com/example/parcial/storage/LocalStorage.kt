package com.example.parcial.storage

import android.content.Context
import com.example.parcial.model.Drink
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalStorage(private val context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "parcial_storage",
            Context.MODE_PRIVATE
        )

    private val gson = Gson()

    fun saveFavorites(favorites: List<Drink>) {

        val json = gson.toJson(favorites)

        prefs.edit()
            .putString("favorites", json)
            .apply()
    }

    fun getFavorites(): MutableList<Drink> {

        val json = prefs.getString("favorites", null)

        return if (json != null) {

            val type =
                object : TypeToken<MutableList<Drink>>() {}.type

            gson.fromJson(json, type)

        } else {
            mutableListOf()
        }
    }

    fun saveCachedDrinks(drinks: List<Drink>) {

        val json = gson.toJson(drinks)

        prefs.edit()
            .putString("cached_drinks", json)
            .apply()
    }

    fun getCachedDrinks(): MutableList<Drink> {

        val json = prefs.getString("cached_drinks", null)

        return if (json != null) {

            val type =
                object : TypeToken<MutableList<Drink>>() {}.type

            gson.fromJson(json, type)

        } else {
            mutableListOf()
        }
    }
}