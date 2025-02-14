package com.jose.pruebtecnicakoaliti.data.model.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jose.pruebtecnicakoaliti.data.model.ingredient.Ingredient

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val isFavorite: Boolean = false,
    val imageUri: String? = null,
    val ingredients: List<Ingredient> = emptyList()
)

