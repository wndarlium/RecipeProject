package com.jose.pruebtecnicakoaliti.data.model.shopping

import com.jose.pruebtecnicakoaliti.data.model.ingredient.Ingredient

data class ShoppingListItem(
    val recipeId: Int,
    val recipeTitle: String,
    val ingredients: List<Ingredient>
)
