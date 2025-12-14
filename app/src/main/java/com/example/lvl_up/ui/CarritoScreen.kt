package com.example.lvl_up.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.data.ItemCarrito
import com.example.lvl_up.ui.theme_Tienda.*
import com.example.lvl_up.viewmodel.CarritoViewModel
import com.example.lvl_up.viewmodel.CarritoViewModelFactory
import java.text.NumberFormat
import java.util.*

@Composable
fun CarritoScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication

    val factory = CarritoViewModelFactory(application.carritoRepository, application.productRepository)
    val viewModel: CarritoViewModel = viewModel(factory = factory)

    val itemsDelCarrito by viewModel.itemsDelCarrito.collectAsState()
    val totalCompra = itemsDelCarrito.sumOf { it.price * it.cantidad }
    val formatoMoneda = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            maximumFractionDigits = 0
        }
    }

    TiendaGamerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(DarkBackground, BlackBackground)
                    )
                )
        ) {
            EncabezadoCarrito(navController)

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Tu Carrito",
                        style = MaterialTheme.typography.headlineLarge,
                        color = NeonCyan,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                if (itemsDelCarrito.isEmpty()) {
                    item {
                        Text(
                            "Tu carrito está vacío.",
                            color = MutedText,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 50.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }
                } else {
                    items(itemsDelCarrito, key = { it.productId }) { item ->
                        FilaProductoCarrito(
                            item = item,
                            formatoMoneda = formatoMoneda,
                            onQuantityChange = { cambio -> viewModel.actualizarCantidad(item, cambio) },
                            onRemoveItem = { viewModel.eliminarItem(item) }
                        )
                    }
                }
            }

            ResumenCompra(
                total = formatoMoneda.format(totalCompra),
                onVaciarCarrito = { viewModel.vaciarCarrito() },
                onPagar = { viewModel.checkout() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncabezadoCarrito(navController: NavController) {
    TopAppBar(
        title = { Text("Mi Tienda PC Gamer", color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = NeonCyan)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = NavbarDark)
    )
}

@Composable
fun FilaProductoCarrito(
    item: ItemCarrito,
    formatoMoneda: NumberFormat,
    onQuantityChange: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = CardSurface.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(formatoMoneda.format(item.price), color = NeonCyan, fontSize = 14.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onQuantityChange(-1) }) {
                    Icon(Icons.Default.Delete, "Quitar uno", tint = MutedText)
                }
                Text("${item.cantidad}", color = Color.White, fontWeight = FontWeight.Bold)

                IconButton(
                    onClick = { onQuantityChange(1) },
                    enabled = item.cantidad < item.stock
                ) {
                    Icon(
                        Icons.Default.Add,
                        "Añadir uno",
                        tint = if (item.cantidad < item.stock) NeonCyan else MutedText
                    )
                }
                IconButton(onClick = onRemoveItem) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = RedAccent)
                }
            }
        }
    }
}

@Composable
fun ResumenCompra(total: String, onVaciarCarrito: () -> Unit, onPagar: () -> Unit) {
    Surface(color = CardSurface, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Total de la Compra: $total",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onPagar,
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("PROCEDER AL PAGO", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onVaciarCarrito,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, RedAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Vaciar Carrito", color = RedAccent)
            }
        }
    }
}