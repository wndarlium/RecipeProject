package com.jose.pruebtecnicakoaliti.ui.screens.recipe


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import coil.compose.rememberImagePainter
import com.jose.pruebtecnicakoaliti.R
import com.jose.pruebtecnicakoaliti.data.local.AppDatabase
import com.jose.pruebtecnicakoaliti.data.model.recipe.Recipe
import com.jose.pruebtecnicakoaliti.data.repository.RecipeRepository
import com.jose.pruebtecnicakoaliti.ui.navigation.Screen
import com.jose.pruebtecnicakoaliti.ui.theme.Beige1
import com.jose.pruebtecnicakoaliti.ui.theme.Brown1
import com.jose.pruebtecnicakoaliti.ui.theme.PlayfairFamily
import com.jose.pruebtecnicakoaliti.ui.theme.Red1
import com.jose.pruebtecnicakoaliti.ui.theme.Yellow1
import com.jose.pruebtecnicakoaliti.viewmodel.LoginViewModel
import com.jose.pruebtecnicakoaliti.viewmodel.RecipeViewModel
import com.jose.pruebtecnicakoaliti.viewmodel.RecipeViewModelFactory

@Composable
fun RecipeListScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactoryInstance()),
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactoryInstance())
) {
    val recipes by recipeViewModel.recipes.collectAsState()
    var showOnlyFavorites by remember { mutableStateOf(false) }
    var isTimeAscending by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val filteredRecipes = recipes.filter { recipe ->
        if (showOnlyFavorites) recipe.isFavorite else true
    }.let { list ->
        if (isTimeAscending) list.sortedBy { it.preparationTime }
        else list.sortedByDescending { it.preparationTime }
    }

    LaunchedEffect(showOnlyFavorites, filteredRecipes) {
        if (showOnlyFavorites && filteredRecipes.isEmpty()) {
            Toast.makeText(context, "No hay recetas favoritas", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddRecipe.route) },
                modifier = Modifier.offset(y = (-10).dp),
                contentColor = Color.White,
                containerColor = Brown1
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar receta")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            CustomBottomBar(
                onLogoutClick = {
                    loginViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.RecipeList.route) { inclusive = true }
                    }
                },
                onCartClick = { navController.navigate(Screen.ShoppingCart.route) },
                onFilterTimeClick = {
                    isTimeAscending = !isTimeAscending
                    Toast.makeText(
                        context,
                        if (isTimeAscending) "Filtrando de menor a mayor"
                        else "Filtrando de mayor a menor",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onFilterFavoritesClick = { showOnlyFavorites = !showOnlyFavorites },
                isFavoritesFilterActive = showOnlyFavorites,
                isTimeAscending = isTimeAscending
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 15.dp)
                .padding(horizontal = 16.dp)
        ) {
            if (filteredRecipes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron recetas",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    )
                }
            } else {
                LazyColumn {
                    items(filteredRecipes) { recipe ->
                        RecipeItem(recipe = recipe, onClick = {
                            navController.navigate(Screen.RecipeDetail.createRoute(recipe.id))
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Beige1)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(8.dp)) {
                recipe.imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(data = uri),
                        contentDescription = "Imagen de la receta",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = PlayfairFamily,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            text = "Tiempo: ",
                            color = Red1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${recipe.preparationTime} min",
                            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Serif)
                        )
                    }
                }
            }
            if (recipe.isFavorite) {
                Text(
                    text = "★ Favorita",
                    color = Yellow1,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun CustomBottomBar(
    onCartClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onFilterTimeClick: () -> Unit,
    onFilterFavoritesClick: () -> Unit,
    isFavoritesFilterActive: Boolean,
    isTimeAscending: Boolean
) {
    Surface(
        tonalElevation = 4.dp,
        color = Brown1,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onLogoutClick,
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
            }
            IconButton(
                onClick = onCartClick,
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
            }
            IconButton(
                onClick = onFilterTimeClick,
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isTimeAscending) R.drawable.baseline_filter_list_24
                        else R.drawable.baseline_filter_list_off_24
                    ),
                    contentDescription = if (isTimeAscending) "Filtrar por tiempo ascendente"
                    else "Filtrar por tiempo descendente"
                )
            }
            IconButton(
                onClick = onFilterFavoritesClick,
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavoritesFilterActive) R.drawable.baseline_star_24
                        else R.drawable.baseline_star_border_24
                    ),
                    contentDescription = if (isFavoritesFilterActive) "Favoritos activados"
                    else "Favoritos desactivados"
                )
            }
        }
    }
}


@Composable
fun RecipeViewModelFactoryInstance(): RecipeViewModelFactory {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = RecipeRepository(database.recipeDao())
    return RecipeViewModelFactory(repository)
}

@Composable
fun LoginViewModelFactoryInstance(): ViewModelProvider.Factory {
    val context = LocalContext.current
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(context) as T
        }
    }
}