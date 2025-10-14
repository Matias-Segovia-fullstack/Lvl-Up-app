package com.example.lvl_up.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AdminTheme(content: @Composable () -> Unit) {

    val DarkColorScheme = darkColorScheme(
        primary = Accent,
        secondary = FondoPanel,
        background = FondoDark,
        surface = FondoPanel,
        onPrimary = TextoPrincipal,
        onSecondary = TextoPrincipal,
        onBackground = TextoPrincipal,
        error = ErrorColor
    )

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
