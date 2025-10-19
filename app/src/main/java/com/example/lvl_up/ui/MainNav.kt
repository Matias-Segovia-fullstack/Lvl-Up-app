package com.example.lvl_up.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lvl_up.TiendaScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "admin") {
        composable("admin") { AdminScreen(navController) }
        composable("user") { UserScreen(navController) }
        composable("productos") { ProductScreen(navController) }
        composable ("editProducto") { EditProducto(navController)}
        composable("crearProducto") { CreateProduct(navController)}
        composable ("crearUsuario"){ CreateUser(navController) }
        composable ("tienda"){ TiendaScreen(navController) }
        // composable ("editUsuario") { EditUsuarioScreen(navController) }
    }
}