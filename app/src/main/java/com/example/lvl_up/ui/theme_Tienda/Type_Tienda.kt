package com.example.lvl_up.ui.theme_Tienda

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Configuración de la tipografía por defecto.
// Se asume una fuente sans-serif si no se proporciona una personalizada.
val AppTypography = Typography(
    // Estilo para títulos principales (simulando .catalogo-title, .signup-title)
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        letterSpacing = 1.sp
    ),
    // Estilo para subtítulos/títulos de sección (simulando .titulo-seccion)
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    // Estilo para texto normal/botones (simulando .producto-desc)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    // El resto de estilos (title, label, etc.) se quedan por defecto.
)