package com.example.parcial.model

import com.google.gson.annotations.SerializedName

data class DrinkResponse(
    @SerializedName("drinks")
    val drinks: List<Drink>? = null
)

data class Drink(

    @SerializedName("idDrink")
    val id: String,

    @SerializedName("strDrink")
    val name: String,

    @SerializedName("strDrinkThumb")
    val image: String,

    @SerializedName("strCategory")
    val category: String?,

    @SerializedName("strAlcoholic")
    val alcoholic: String?,

    @SerializedName("strGlass")
    val glass: String?,

    @SerializedName("strInstructions")
    val instructions: String?
)