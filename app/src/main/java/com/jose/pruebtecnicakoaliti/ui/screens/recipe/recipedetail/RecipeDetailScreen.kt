package com.jose.pruebtecnicakoaliti.ui.screens.recipe.recipedetail

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jose.pruebtecnicakoaliti.R
import com.jose.pruebtecnicakoaliti.data.model.recipe.Recipe
import com.jose.pruebtecnicakoaliti.data.model.shopping.ShoppingListItem
import com.jose.pruebtecnicakoaliti.ui.navigation.Screen
import com.jose.pruebtecnicakoaliti.ui.screens.recipe.RecipeViewModelFactoryInstance
import com.jose.pruebtecnicakoaliti.ui.theme.Beige1
import com.jose.pruebtecnicakoaliti.ui.theme.Brown1
import com.jose.pruebtecnicakoaliti.ui.theme.PlayfairFamily
import com.jose.pruebtecnicakoaliti.ui.theme.Red1
import com.jose.pruebtecnicakoaliti.ui.theme.Yellow1
import com.jose.pruebtecnicakoaliti.viewmodel.RecipeViewModel
import com.jose.pruebtecnicakoaliti.viewmodel.ListCartViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    navController: NavController,
    recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactoryInstance())
) {
    val listCartViewModel: ListCartViewModel = viewModel(LocalContext.current as ComponentActivity)


    var recipe by remember { mutableStateOf<Recipe?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(recipeId) {
        recipe = recipeViewModel.getRecipeById(recipeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle de Receta",
                        color = Color.White,
                        fontFamily = PlayfairFamily,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (recipe != null) {
                        IconButton(onClick = {
                            recipe?.let {
                                val newFav = !it.isFavorite
                                recipeViewModel.updateRecipe(it.copy(isFavorite = newFav))
                                recipe = it.copy(isFavorite = newFav)
                            }
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (recipe!!.isFavorite) R.drawable.baseline_star_24
                                    else R.drawable.baseline_star_border_24
                                ),
                                contentDescription = "Favorito",
                                tint = Yellow1
                            )
                        }
                    }
                    IconButton(onClick = {
                        recipe?.let {
                            coroutineScope.launch {
                                recipeViewModel.deleteRecipe(it)
                                navController.popBackStack()
                            }
                        }
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Brown1)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    recipe?.let { rec ->
                        listCartViewModel.addItem(
                            ShoppingListItem(
                                recipeId = rec.id,
                                recipeTitle = rec.title,
                                ingredients = rec.ingredients ?: emptyList()
                            )
                        )
                        navController.navigate(Screen.ShoppingCart.route)
                    }
                },
                containerColor = Brown1,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        if (recipe == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val rec = recipe!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rec.imageUri?.let {
                    Image(
                        painter = rememberImagePainter(data = it),
                        contentDescription = "Imagen de la receta",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = rec.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = PlayfairFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Tiempo de preparación: ${rec.preparationTime} min",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Red1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = rec.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                var favorite by remember { mutableStateOf(rec.isFavorite) }
                /*Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = favorite,
                        onCheckedChange = { isChecked ->
                            favorite = isChecked
                            coroutineScope.launch {
                                recipeViewModel.updateRecipe(rec.copy(isFavorite = isChecked))
                            }
                        }
                    )
                    Text("Marcar como favorita")
                }
                 */
                Text(
                    text = "Preparación: ${rec.preparation}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Beige1)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Ingredientes",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (!rec.ingredients.isNullOrEmpty()) {
                            rec.ingredients.forEach { ingredient ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        text = ingredient.quantity,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        modifier = Modifier.width(80.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = ingredient.name,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        } else {
                            Text("No hay ingredientes", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}



