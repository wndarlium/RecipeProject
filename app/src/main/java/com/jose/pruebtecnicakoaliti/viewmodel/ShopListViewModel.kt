package com.jose.pruebtecnicakoaliti.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jose.pruebtecnicakoaliti.data.model.shopping.ShoppingListItem


class ListCartViewModel : ViewModel() {
    var shoppingList by mutableStateOf<List<ShoppingListItem>>(emptyList())
        private set

    fun addItem(item: ShoppingListItem) {
        if (shoppingList.any { it.recipeId == item.recipeId }) {
            return
        }
        shoppingList = shoppingList + item
    }


    fun toggleIngredientChecked(recipeId: Int, name: String) {
        shoppingList = shoppingList.map { item ->
            if (item.recipeId == recipeId) {
                val updatedIngredients = item.ingredients.map { ing ->
                    if (ing.name == name) ing.copy(isChecked = !ing.isChecked)
                    else ing
                }
                item.copy(ingredients = updatedIngredients)
            } else item
        }
    }

}


/*class ShoppingCartViewModelFactory(private val repository: ShoppingListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingCartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
 */


@Composable
fun rememberShoppingCartViewModel(): ListCartViewModel = viewModel()

