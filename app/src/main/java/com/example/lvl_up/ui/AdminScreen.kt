package com.example.lvl_up.ui

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lvl_up.ui.theme_Admin.*
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.viewmodel.AdminViewModel
import com.example.lvl_up.viewmodel.AdminViewModelFactory


@Composable
fun AdminScreen(navController: NavController) {

    val context = LocalContext.current
    // Accede a los repositorios de la Application
    val application = context.applicationContext as LvlUpApplication
    val pRepo = application.productRepository
    val uRepo = application.userRepository

    // Crea la Factory con ambos repositorios
    val factory = AdminViewModelFactory(pRepo, uRepo)
    val viewModel: AdminViewModel = viewModel(factory = factory)

    val productCount by viewModel.productCountState.collectAsState()
    val userCount by viewModel.userCountState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd)
                )
            )
    ) {
        // CAMBIO 1: Usamos Column para apilar el Contenido y la Barra Inferior
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
        ) {

            // --- Contenido principal (Peso se aplica aquí para ocupar el espacio restante) ---
            MainContent(
                productCount = productCount, // 🚨 Pasar el conteo real
                userCount = userCount,
                modifier = Modifier
                    .weight(1f) // ⬅️ Nuevo peso para ocupar todo el espacio vertical sobrante
                    .padding(24.dp)
            )

            // --- Barra Inferior (SidebarMenu) ---
            // Se usa el peso 0.585f que tenías, pero el valor final será 1f en horizontal
            DownbarMenu(navController = navController, modifier = Modifier.fillMaxWidth()) // ⬅️ Se usa fillMaxWidth en lugar de weight
        }
    }
}

@Composable
fun DownbarButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    isLogout: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = FondoPanel
    val textColor = if (isLogout) ErrorColor else TextoPrincipal
    val iconTint = if (isLogout) ErrorColor else Accent

    Column (
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 0.dp) // Ajuste vertical
            .background(backgroundColor, shape = RoundedCornerShape(0.dp)) // Sin radio de esquina en la barra
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = text, tint = iconTint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp)) // Espacio vertical
        Text(text, color = textColor, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp) // Texto más pequeño
    }
}

@Composable
fun DownbarMenu(navController: NavController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(FondoPanel, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        DownbarButton(Icons.Default.Home, "Inicio") {
            navController.navigate("admin")
        }
        DownbarButton(Icons.Default.ShoppingCart, "Productos") {
            navController.navigate("productos")
        }
        DownbarButton(Icons.Default.Person, "Usuarios") {
            navController.navigate("user")
        }

        DownbarButton(Icons.Default.ExitToApp, "Salir", isLogout = true) {
            navController.navigate("login")
        }
    }
}

@Composable
fun MainContent(
    productCount: Int,
    userCount: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Bienvenido Administrador",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 10.dp)

        )

        Text(
            "Selecciona una opción del menú para comenzar a gestionar la tienda.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            WidgetCard("Productos", productCount.toString())
            WidgetCard("Usuarios", userCount.toString())
            //WidgetCard("Pedidos", "58")
        }
    }
}

@Composable
fun WidgetCard(title: String, value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = FondoPanel,
        shadowElevation = 8.dp,
        modifier = Modifier
            .width(150.dp)
            .height(130.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}
