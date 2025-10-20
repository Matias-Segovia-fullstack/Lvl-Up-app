// En app/src/main/java/com/example/lvl_up/ui/ProductoVentaScreen.kt

package com.example.lvl_up.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.R
import com.example.lvl_up.data.Product
import com.example.lvl_up.ui.theme_Tienda.*
import com.example.lvl_up.viewmodel.CarritoViewModel
import com.example.lvl_up.viewmodel.CarritoViewModelFactory
import com.example.lvl_up.viewmodel.ProductViewModel
import com.example.lvl_up.viewmodel.ProductViewModelFactory
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductoVentaScreen(navController: NavController, productId: Int) {
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication

    // ViewModel para obtener los datos del producto
    val productFactory = ProductViewModelFactory(application.productRepository)
    val productViewModel: ProductViewModel = viewModel(factory = productFactory)

    // ViewModel para las acciones del carrito
    val carritoFactory = CarritoViewModelFactory(application.carritoRepository, application.productRepository)
    val carritoViewModel: CarritoViewModel = viewModel(factory = carritoFactory)

    var product by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf(1) }

    // Busca los detalles del producto una sola vez cuando la pantalla se carga
    LaunchedEffect(productId) {
        product = productViewModel.getProductForEdit(productId)
    }

    // Formateador para pesos chilenos
    val clpFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            maximumFractionDigits = 0
        }
    }

    TiendaGamerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(DarkBackground, BlackBackground)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (product == null) {
                CircularProgressIndicator(color = NeonCyan)
            } else {
                product?.let { p ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Imagen del producto (usando un placeholder)
                        Image(
                            painter = painterResource(id = R.drawable.placeholder_product),
                            contentDescription = p.name,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f)
                                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Nombre del producto
                        Text(
                            text = p.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Fila para Precio y Stock
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = clpFormatter.format(p.price.replace(".", "").toLongOrNull() ?: 0L),
                                color = PriceSuccess,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Stock: ${p.stock}",
                                color = MutedText,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Selector de cantidad
                        QuantitySelector(
                            quantity = quantity,
                            onQuantityChange = { newQuantity ->
                                if (newQuantity > 0 && newQuantity <= p.stock) {
                                    quantity = newQuantity
                                }
                            },
                            stock = p.stock
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón para agregar al carrito
                        Button(
                            onClick = {
                                carritoViewModel.addToCart(p, quantity)
                                navController.popBackStack() // Vuelve a la pantalla anterior
                            },
                            enabled = p.stock > 0,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = DarkButton),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("AGREGAR AL CARRITO", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Botón para salir
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, NeonCyan),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("SALIR", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NeonCyan)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    stock: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .background(CardSurface, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp)
    ) {
        IconButton(
            onClick = { onQuantityChange(quantity - 1) },
            enabled = quantity > 1
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Quitar uno", tint = MutedText)
        }

        Text(
            text = quantity.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.widthIn(min = 40.dp),
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = { onQuantityChange(quantity + 1) },
            enabled = quantity < stock
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir uno", tint = NeonCyan)
        }
    }
}