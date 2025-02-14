package com.jose.pruebtecnicakoaliti.ui.screens.shopping

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jose.pruebtecnicakoaliti.viewmodel.ListCartViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.jose.pruebtecnicakoaliti.data.model.recipe.Recipe
import com.jose.pruebtecnicakoaliti.data.model.shopping.ShoppingListItem
import com.jose.pruebtecnicakoaliti.ui.screens.recipe.RecipeViewModelFactoryInstance
import com.jose.pruebtecnicakoaliti.ui.theme.Beige1
import com.jose.pruebtecnicakoaliti.ui.theme.Brown1
import com.jose.pruebtecnicakoaliti.ui.theme.PlayfairFamily
import com.jose.pruebtecnicakoaliti.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(
    navController: NavController,
    @SuppressLint("ContextCastToActivity") listCartViewModel: ListCartViewModel = viewModel(LocalContext.current as ComponentActivity),
    recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactoryInstance())
) {
    var showRecipeSelectionDialog by remember { mutableStateOf(false) }
    var showDuplicateDialog by remember { mutableStateOf(false) }

    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())
    val shoppingList = listCartViewModel.shoppingList

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Lista de Compras",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = PlayfairFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White

                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Brown1,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showRecipeSelectionDialog = true },
                containerColor = Brown1,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir receta")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (shoppingList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay recetas en el carrito", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(shoppingList) { item ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Beige1)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Ingredientes de ${item.recipeTitle}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                item.ingredients.forEach { ing ->
                                    var isChecked by remember { mutableStateOf(ing.isChecked) }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Checkbox(
                                            checked = isChecked,
                                            onCheckedChange = { checked ->
                                                isChecked = checked
                                                listCartViewModel.toggleIngredientChecked(
                                                    item.recipeId,
                                                    ing.name
                                                )
                                            }
                                        )
                                        val textStyle = if (isChecked)
                                            MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                                        else MaterialTheme.typography.bodyMedium
                                        Text(
                                            text = "${ing.quantity} - ${ing.name}",
                                            style = textStyle
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showRecipeSelectionDialog) {
        RecipeSelectionDialog(
            recipes = recipes,
            onDismiss = { showRecipeSelectionDialog = false },
            onRecipeSelected = { recipe ->
                if (shoppingList.any { it.recipeId == recipe.id }) {
                    showDuplicateDialog = true
                } else {
                    listCartViewModel.addItem(
                        ShoppingListItem(
                            recipeId = recipe.id,
                            recipeTitle = recipe.title,
                            ingredients = recipe.ingredients ?: emptyList()
                        )
                    )
                }
                showRecipeSelectionDialog = false
            }
        )
    }

    if (showDuplicateDialog) {
        AlertDialog(
            onDismissRequest = { showDuplicateDialog = false },
            title = {
                Text(
                    text = "Atención",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Brown1
                    )
                )
            },
            text = {
                Text(
                    text = "Ya existe una lista de compras para esta receta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDuplicateDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Brown1)
                ) {
                    Text("Aceptar", color = Color.White)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Beige1,
            tonalElevation = 8.dp
        )
    }

}


@Composable
fun RecipeSelectionDialog(
    recipes: List<Recipe>,
    onDismiss: () -> Unit,
    onRecipeSelected: (Recipe) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Selecciona una receta",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Brown1
                )
            )
        },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(recipes) { recipe ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRecipeSelected(recipe) }
                            .padding(12.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(8.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Brown1)
            ) {
                Text("Cancelar", color = Color.White)
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Beige1,
        tonalElevation = 8.dp
    )
}


