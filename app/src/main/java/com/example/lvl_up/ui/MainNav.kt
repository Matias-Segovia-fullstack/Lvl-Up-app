package com.example.lvl_up.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "admin") {
        composable("admin") { AdminScreen(navController) }
        composable("user") { UserScreen(navController) } // Added UserScreen route
        composable("productos") { ProductScreen(navController) }
        composable ("editProducto") { EditProducto((navController))}
        // You might need to add a route for "editUsuario" if that screen exists:
        // composable ("editUsuario") { EditUsuarioScreen(navController) }
    }
}