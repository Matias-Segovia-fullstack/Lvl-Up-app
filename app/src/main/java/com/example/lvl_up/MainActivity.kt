package com.example.lvl_up

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lvl_up.ui.AdminScreen
import com.example.lvl_up.ui.ProductScreen
import com.example.lvl_up.ui.theme.AdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "admin") {
                    composable("admin") { AdminScreen(navController) }
                    composable("productos") { ProductScreen(navController) }
                }
            }
        }
    }
}
