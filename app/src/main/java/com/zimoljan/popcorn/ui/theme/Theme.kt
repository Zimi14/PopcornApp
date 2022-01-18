package com.zimoljan.popcorn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


val LightColors = lightColors(
    primary = Color.Black,
    primaryVariant = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

val DarkColors = darkColors(
    primary = Color.White,
    primaryVariant = Color.White,
    surface = Color.Black,
    onSurface = Color.White
)

@Composable
fun Popcorn(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = PopcornTypography,
        colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}