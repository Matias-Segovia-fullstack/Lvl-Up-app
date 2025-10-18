package com.example.lvl_up.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lvl_up.ui.theme.*
import androidx.navigation.NavController


@Composable
fun AdminScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd)
                )
            )
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // --- Sidebar ---
            SidebarMenu(navController = navController, modifier = Modifier.weight(0.585f))

            // --- Contenido principal ---
            MainContent(
                modifier = Modifier
                    .weight(0.75f)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun SidebarButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    isLogout: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isLogout) ErrorColor.copy(alpha = 0.15f) else FondoPanel
    val textColor = if (isLogout) ErrorColor else TextoPrincipal
    val iconTint = if (isLogout) ErrorColor else Accent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = iconTint)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = textColor, style = MaterialTheme.typography.bodyLarge, fontSize = 12.sp)
    }
}

@Composable
fun SidebarMenu(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(FondoPanel, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Mi Tienda PC Gamer",
            color = Accent,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(30.dp))

        SidebarButton (Icons.Default.Home, "Inicio"){
            navController.navigate("admin")
        }
        SidebarButton (Icons.Default.ShoppingCart, "Productos"){
            navController.navigate("productos")
        }
        SidebarButton (Icons.Default.Person, "Usuarios"){
            navController.navigate("user")
        }


        Spacer(modifier = Modifier.weight(1f))

        SidebarButton (Icons.Default.ExitToApp, "Salir", isLogout = true){ }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Bienvenido Administrador",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 10.dp)

        )

        Text(
            "Selecciona una opción del menú para comenzar a gestionar la tienda.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 24.dp)
        )

        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            WidgetCard("Productos", "125")
            WidgetCard("Usuarios", "320")
            WidgetCard("Pedidos", "58")
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
