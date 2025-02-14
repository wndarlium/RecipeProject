package com.jose.pruebtecnicakoaliti.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jose.pruebtecnicakoaliti.ui.screens.login.LoginScreen
import com.jose.pruebtecnicakoaliti.ui.screens.recipe.RecipeListScreen
import com.jose.pruebtecnicakoaliti.ui.screens.recipe.addrecipe.AddRecipeScreen
import com.jose.pruebtecnicakoaliti.ui.screens.recipe.recipedetail.RecipeDetailScreen
import com.jose.pruebtecnicakoaliti.ui.screens.shopping.ShopListScreen
import com.jose.pruebtecnicakoaliti.ui.screens.splash.SplashScreen


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object RecipeList : Screen("recipeList")
    object AddRecipe : Screen("addRecipe")
    object ShoppingCart : Screen("shoppingCart")
    object RecipeDetail : Screen("recipeDetail/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipeDetail/$recipeId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RecipeList.route) {
            RecipeListScreen(navController = navController)
        }
        composable(Screen.AddRecipe.route) {
            AddRecipeScreen(navController = navController)
        }
        composable(Screen.ShoppingCart.route) {
            ShopListScreen(navController = navController)
        }
        composable(Screen.RecipeDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: 0
            RecipeDetailScreen(recipeId = recipeId, navController = navController)
        }
    }
}
