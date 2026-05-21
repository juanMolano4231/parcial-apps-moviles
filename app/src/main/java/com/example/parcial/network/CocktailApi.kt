package com.example.parcial.network

import com.example.parcial.model.DrinkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("search.php?s=margarita")
    suspend fun getDrinks(): DrinkResponse

    @GET("lookup.php")
    suspend fun getDrinkById(
        @Query("i") id: String
    ): DrinkResponse
}