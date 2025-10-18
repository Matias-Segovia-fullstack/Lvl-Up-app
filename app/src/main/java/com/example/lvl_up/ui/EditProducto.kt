package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lvl_up.data.sampleProducts
import com.example.lvl_up.ui.theme_Admin.FondoGradientEnd
import com.example.lvl_up.ui.theme_Admin.FondoGradientStart

@Composable
fun EditProducto(navController: NavController) {
    val products = sampleProducts

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd) // Mapeo a body background
                )
            )
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Reutilizamos el SidebarMenu de AdminScreen.kt
            DownbarMenu(
                navController = navController,
                modifier = Modifier.weight(0.55f)
            )

            // Contenido principal (Tabla de productos)
            ProductListContent(
                products = products,
                navController = navController,
                modifier = Modifier
                    .weight(0.75f)
                    .padding(24.dp)
            )
        }
    }
}