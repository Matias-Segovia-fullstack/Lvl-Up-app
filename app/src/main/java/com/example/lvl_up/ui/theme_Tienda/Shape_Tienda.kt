package com.example.lvl_up.ui.theme_Tienda


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Definición de Formas (Shapes) basadas en el CSS
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp), // 4px
    small = RoundedCornerShape(8.dp),     // 8px (ej. botones, tarjetas)
    medium = RoundedCornerShape(12.dp),    // 12px (ej. banners, contenedores intermedios)
    large = RoundedCornerShape(18.dp)     // 18px (ej. contenedores principales)
)