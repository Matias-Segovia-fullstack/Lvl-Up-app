package com.example.lvl_up.ui.theme_Tienda


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TiendaGamerTheme(
    content: @Composable () -> Unit
) {
    // Definición del Color Scheme (esquema de colores)
    val colorScheme = darkColorScheme(
        primary = NeonCyan,          // Acento principal
        onPrimary = DarkButton,      // Texto sobre el acento principal
        primaryContainer = DarkBackground,
        secondary = DarkButton,      // Fondo de elementos como Cards de producto
        onSecondary = Color.White,
        tertiary = AccentBlue,       // Acento secundario (Azul)
        background = DarkBackground, // Fondo principal (para gradiente)
        onBackground = Color.White,
        surface = CardSurface,       // Fondo de contenedores (Surface)
        onSurface = Color.White,
        error = RedAccent,
        onError = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}