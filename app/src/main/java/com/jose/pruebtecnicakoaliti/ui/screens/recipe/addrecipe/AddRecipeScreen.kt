package com.jose.pruebtecnicakoaliti.ui.screens.recipe.addrecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jose.pruebtecnicakoaliti.R
import com.jose.pruebtecnicakoaliti.data.model.ingredient.Ingredient
import com.jose.pruebtecnicakoaliti.data.model.recipe.Recipe
import com.jose.pruebtecnicakoaliti.ui.navigation.Screen
import com.jose.pruebtecnicakoaliti.ui.screens.recipe.RecipeViewModelFactoryInstance
import com.jose.pruebtecnicakoaliti.ui.theme.Beige1
import com.jose.pruebtecnicakoaliti.ui.theme.Brown1
import com.jose.pruebtecnicakoaliti.ui.theme.PlayfairFamily
import com.jose.pruebtecnicakoaliti.ui.theme.Yellow1
import com.jose.pruebtecnicakoaliti.viewmodel.RecipeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactoryInstance())
) {
    val context = LocalContext.current
    val recipes by recipeViewModel.recipes.collectAsState(initial = emptyList())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var preparation by remember { mutableStateOf("") }

    val ingredients = remember { mutableStateListOf<Ingredient>() }
    fun updateIngredient(index: Int, ingredient: Ingredient) {
        ingredients[index] = ingredient
    }

    var showTitleExistsDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageUri = it
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agregar Receta",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Brown1)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isError = title.isBlank()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isError = description.isBlank()
            )
            OutlinedTextField(
                value = prepTime,
                onValueChange = { prepTime = it },
                label = { Text("Tiempo de preparación (min)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                isError = prepTime.toIntOrNull() == null || prepTime.toIntOrNull()!! <= 0
            )
            OutlinedTextField(
                value = preparation,
                onValueChange = { preparation = it },
                label = { Text("Preparación") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                isError = preparation.isBlank()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { isFavorite = !isFavorite }) {
                    Icon(
                        painter = painterResource(
                            id = if (isFavorite) R.drawable.baseline_star_24
                            else R.drawable.baseline_star_border_24
                        ),
                        contentDescription = if (isFavorite) "Favorito activado" else "Favorito desactivado",
                        tint = Yellow1,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Marcar como favorita", style = MaterialTheme.typography.bodyMedium)
            }
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Brown1)
            ) {
                Text("Seleccionar imagen", color = Color.White)
            }
            imageUri?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Beige1)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    IngredientsSection(
                        ingredients = ingredients,
                        onIngredientChange = ::updateIngredient,
                        onAddIngredient = { ingredients.add(Ingredient("", "")) },
                        onRemoveIngredient = { if (ingredients.isNotEmpty()) ingredients.removeAt(ingredients.lastIndex) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val time = prepTime.toIntOrNull() ?: 0
                    if (title.isNotBlank() && description.isNotBlank() && time > 0) {
                        if (recipes.any { it.title.equals(title, ignoreCase = true) }) {
                            showTitleExistsDialog = true
                        } else {
                            val recipe = Recipe(
                                title = title,
                                description = description,
                                preparationTime = time,
                                preparation = preparation,
                                isFavorite = isFavorite,
                                imageUri = imageUri?.toString(),
                                ingredients = ingredients.toList()
                            )
                            recipeViewModel.addRecipe(recipe)
                            navController.navigate(Screen.RecipeList.route) {
                                popUpTo(Screen.AddRecipe.route) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Brown1)
            ) {
                Text("Guardar Receta", color = Color.White)
            }
        }
    }

    if (showTitleExistsDialog) {
        AlertDialog(
            onDismissRequest = { showTitleExistsDialog = false },
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
                    text = "El título ya está en uso, por favor elige otro.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = { showTitleExistsDialog = false },
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


