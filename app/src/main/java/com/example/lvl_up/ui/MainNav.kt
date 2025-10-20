package com.example.lvl_up.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lvl_up.CategoriasScreen

// ✅ IMPORTACIONES DE PANTALLAS
import com.example.lvl_up.PCGamerGuideScreen
import com.example.lvl_up.TiendaScreen
import com.example.lvl_up.RetroGamesScreen



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
        composable("retroGamesDetail") { RetroGamesScreen(navController) }
        composable("pcGamerGuide") { PCGamerGuideScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable(route = "catalog") { CategoriasScreen(navController) }
        composable("carrito") { CarritoScreen(navController)}

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

        composable(
            route = "product_categories/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            ProductCategoriesScreen(
                navController = navController,
                category = backStackEntry.arguments?.getString("category")
            )
        }

        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductoVentaScreen(navController = navController, productId = productId)
        }

    }
}

