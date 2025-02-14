package com.jose.pruebtecnicakoaliti.data.model.ingredient

data class Ingredient(
    val name: String,
    val quantity: String,
    val isChecked: Boolean = false
)
