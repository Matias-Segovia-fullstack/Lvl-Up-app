package com.example.lvl_up

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lvl_up.ui.AdminScreen
import com.example.lvl_up.ui.CreateProduct
import com.example.lvl_up.ui.CreateUser
import com.example.lvl_up.ui.ProductScreen
import com.example.lvl_up.ui.UserScreen
import com.example.lvl_up.ui.theme_Admin.AdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "tienda") {
                    composable ("tienda"){ TiendaScreen(navController) }
                    composable("admin") { AdminScreen(navController) }
                    composable("productos") { ProductScreen(navController) }
                    composable("user") { UserScreen(navController) }
                    composable ("crearProducto"){ CreateProduct((navController)) }
                    composable ("crearUsuario"){ CreateUser(navController) }
                }
            }
        }
    }
}
//comentario test