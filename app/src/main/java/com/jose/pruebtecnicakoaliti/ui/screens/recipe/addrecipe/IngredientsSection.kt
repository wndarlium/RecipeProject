package com.jose.pruebtecnicakoaliti.ui.screens.recipe.addrecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jose.pruebtecnicakoaliti.data.model.ingredient.Ingredient
import androidx.compose.ui.res.painterResource
import com.jose.pruebtecnicakoaliti.R

@Composable
fun IngredientsSection(
    ingredients: MutableList<Ingredient>,
    onIngredientChange: (Int, Ingredient) -> Unit,
    onAddIngredient: () -> Unit,
    onRemoveIngredient: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ingredientes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onAddIngredient) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Añadir ingrediente",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = onRemoveIngredient) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_circle_24),
                    contentDescription = "Eliminar ingrediente",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        ingredients.forEachIndexed { index, ingredient ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    OutlinedTextField(
                        value = ingredient.name,
                        onValueChange = { newName ->
                            onIngredientChange(index, ingredient.copy(name = newName))
                        },
                        label = { Text("Ingrediente") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        isError = ingredient.name.isBlank()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = ingredient.quantity,
                        onValueChange = { newQuantity ->
                            onIngredientChange(index, ingredient.copy(quantity = newQuantity))
                        },
                        label = { Text("Cantidad") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        isError = ingredient.quantity.isBlank()
                    )
                }
                if (ingredient.name.isBlank() || ingredient.quantity.isBlank()) {
                    Text(
                        text = "Todos los campos son obligatorios",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}



