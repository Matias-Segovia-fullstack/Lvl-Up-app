package com.example.lvl_up.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lvl_up.TiendaScreen

@Composable
fun MainNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("admin") { AdminScreen(navController) }
        composable("user") { UserScreen(navController) }
        composable("productos") { ProductScreen(navController) }
        composable("crearProducto") { CreateProduct(navController)}
        composable ("crearUsuario"){ CreateUser(navController) }
        composable ("tienda"){ TiendaScreen(navController) }

        composable(
            route = "edit_product/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            EditProduct(navController = navController, productId = productId)
        }

        composable(
            route = "edit_user/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            EditUser(navController = navController, userId = userId)
        }
    }
}