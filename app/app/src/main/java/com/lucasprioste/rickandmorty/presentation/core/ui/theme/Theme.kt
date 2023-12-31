package com.lucasprioste.rickandmorty.presentation.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = BlueNight,
    onPrimary = White,
    background = Gray,
    onBackground = White,
    surface = Green,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = BlueLight,
    onPrimary = Black,
    background = White,
    onBackground = Black,
    surface = Green,
    onSurface = White
)

@Composable
fun RickAndMortyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}