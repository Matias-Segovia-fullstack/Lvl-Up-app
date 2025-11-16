package com.example.lvl_up.ui

import android.R
// <<< CAMBIO 1: Imports necesarios para el Calendario
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.CalendarContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
// ---
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
import androidx.core.content.ContextCompat // <<< CAMBIO 2
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lvl_up.ui.theme_Admin.*
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.viewmodel.AdminViewModel
import com.example.lvl_up.viewmodel.AdminViewModelFactory
import java.util.Calendar // <<< CAMBIO 3


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

            // <<< CAMBIO 4: Aquí ponemos el botón de prueba
            Spacer(modifier = Modifier.height(20.dp))
            EjemploDeCalendario()
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


// --- <<< CAMBIO 5: AÑADIMOS LAS FUNCIONES DEL CALENDARIO AL FINAL ---

@Composable
fun EjemploDeCalendario() {
    val context = LocalContext.current

    // 1. Launcher para pedir el permiso de CALENDARIO
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Si el permiso FUE concedido, lanza el calendario
            lanzarIntentCalendario(context)
        } else {
            // Opcional: Muestra mensaje de error
        }
    }

    Button(
        onClick = {
            // 2. Lógica del botón
            when (PackageManager.PERMISSION_GRANTED) {
                // Revisa si ya tenemos el permiso
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_CALENDAR
                ) -> {
                    // Si ya lo tenemos, lanza el calendario
                    lanzarIntentCalendario(context)
                }
                else -> {
                    // Si NO lo tenemos, pide el permiso
                    permissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
                }
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Accent)
    ) {
        Text(text = "🗓️ Añadir evento (Prueba)", color = FondoDark)
    }
}

// 3. Esta es la función que PREPARA los datos para la app de Calendario
fun lanzarIntentCalendario(context: Context) {
    // Fija una fecha de ejemplo (ej. 2 horas desde ahora)
    val startTime = Calendar.getInstance()
    startTime.add(Calendar.HOUR_OF_DAY, 2)
    val endTime = Calendar.getInstance()
    endTime.add(Calendar.HOUR_OF_DAY, 3)

    // Crea el Intent para INSERTAR un evento
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, "Lanzamiento Nuevo Funko")
        putExtra(CalendarContract.Events.EVENT_LOCATION, "Tienda Lvl-Up")
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.timeInMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
        putExtra(CalendarContract.Events.DESCRIPTION, "¡No te pierdas el lanzamiento de la nueva figura!")
    }

    // Lanza el intent (esto abrirá la app de calendario del usuario)
    context.startActivity(intent)
}