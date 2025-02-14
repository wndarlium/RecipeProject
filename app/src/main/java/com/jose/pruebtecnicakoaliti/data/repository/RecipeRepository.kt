package com.jose.pruebtecnicakoaliti.data.repository


import com.jose.pruebtecnicakoaliti.data.local.RecipeDao
import com.jose.pruebtecnicakoaliti.data.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insertRecipe(recipe: Recipe) = recipeDao.insertRecipe(recipe)

    suspend fun updateRecipe(recipe: Recipe) = recipeDao.updateRecipe(recipe)

    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)

    suspend fun getRecipeById(id: Int): Recipe? = recipeDao.getRecipeById(id)
}


