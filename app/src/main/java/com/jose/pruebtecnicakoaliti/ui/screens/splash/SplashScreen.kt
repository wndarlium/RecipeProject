package com.jose.pruebtecnicakoaliti.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jose.pruebtecnicakoaliti.R
import com.jose.pruebtecnicakoaliti.data.datastore.SessionManager
import com.jose.pruebtecnicakoaliti.ui.theme.PlayfairFamily
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val alpha = remember { Animatable(1f) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var textVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        textVisible = true

        delay(4000)

        alpha.animateTo(0f, animationSpec = tween(1000))
        if (sessionManager.isLoggedIn()) {
            navController.navigate("recipeList") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha.value)
    ) {
        LottieAnimationComponent(
            modifier = Modifier.align(Alignment.Center)
        )
        AnimatedVisibility(
            visible = textVisible,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 1000)
            ) + fadeIn(animationSpec = tween(durationMillis = 1000)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Recipe APP",
                style = MaterialTheme.typography.titleLarge.copy( fontFamily = PlayfairFamily, fontWeight = FontWeight.ExtraLight),
                color = Color.Black,
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun LottieAnimationComponent(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.recipe_book_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}
