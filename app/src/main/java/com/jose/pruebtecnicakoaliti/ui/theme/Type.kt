package com.jose.pruebtecnicakoaliti.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jose.pruebtecnicakoaliti.R

// Set of Material typography styles to start with

val PlayfairFamily = FontFamily(
    Font(R.font.playfairdisplay_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.playfairdisplay_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.playfairdisplay_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.playfairdisplay_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.playfairdisplay_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(R.font.playfairdisplay_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.playfairdisplay_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.playfairdisplay_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.playfairdisplay_extrabold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(R.font.playfairdisplay_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.playfairdisplay_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(R.font.playfairdisplay_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic)
)



val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = PlayfairFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)