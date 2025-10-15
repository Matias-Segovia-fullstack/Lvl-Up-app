package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lvl_up.ui.theme.* import com.example.lvl_up.data.Product
import com.example.lvl_up.data.sampleProducts


@Composable
fun ProductScreen(navController: NavController) {
    val products = sampleProducts

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd) // Mapeo a body background
                )
            )
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Reutilizamos el SidebarMenu de AdminScreen.kt
            SidebarMenu(
                navController = navController,
                modifier = Modifier.weight(0.55f)
            )

            // Contenido principal (Tabla de productos)
            ProductListContent(
                products = products,
                navController = navController,
                modifier = Modifier
                    .weight(0.75f)
                    .padding(24.dp)
            )
        }
    }
}

// ----------------------------------------------------------------------------------

@Composable
fun ProductListContent(products: List<Product>, navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        // --- HEADER (productos-header) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Productos",
                fontSize = 2.1.sp * 16, // Simula el tamaño del h1
                color = Accent, // Color #03ffe2 del CSS
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )

            // Botón "Crear producto" (.btn-crear-producto)
            Button(
                onClick = { /* Navegar a crear_producto.html */ navController.navigate("crear_producto") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent, // background: #03ffe2
                    contentColor = FondoPanel // color: #23293e
                ),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Crear producto", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(7.dp))
                Text("Crear producto", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        // --- TABLE WRAPPER (productos-table-wrapper) ---
        Surface(
            shape = RoundedCornerShape(15.dp), // border-radius: 15px
            color = FondoPanel, // background: #23293e
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(24.dp)) {
                // Encabezado de la tabla (th)
                item { ProductTableHeader() }

                // Filas de los productos (tr)
                items(products) { product ->
                    ProductRow(product)
                    // Separador de fila (simulando el border-bottom)
                    Divider(color = TextoSecundario.copy(alpha = 0.3f), thickness = 1.dp)
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------------

@Composable
fun ProductTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(FondoDark, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)) // background: #181c25
            .padding(vertical = 14.dp),
    ) {
        val headerColor = Accent
        val headerWeight = FontWeight.SemiBold
        val textSize = 14.sp

        Text("ID", Modifier.weight(0.5f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
        Text("Imagen", Modifier.weight(0.8f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
        Text("Nombre", Modifier.weight(2f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
        Text("Categoría", Modifier.weight(1f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
        Text("Precio", Modifier.weight(1f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
        Text("Stock", Modifier.weight(0.7f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
        Text("Acciones", Modifier.weight(1f), color = headerColor, fontWeight = headerWeight, fontSize = textSize)
    }
}

// ----------------------------------------------------------------------------------

@Composable
fun ProductRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ID
        Text(product.id.toString(), Modifier.weight(0.5f), color = TextoPrincipal, fontSize = 14.sp)

        // Imagen (Placeholder que simula .producto-img)
        Box(
            modifier = Modifier
                .weight(0.8f)
                .size(52.dp)
                .background(FondoDark, shape = RoundedCornerShape(10.dp))
                .border(2.dp, Accent, RoundedCornerShape(10.dp))
        ) {
            Text(text = "IMG", fontSize = 10.sp, modifier = Modifier.align(Alignment.Center), color = TextoSecundario)
        }

        // Nombre, Categoría, Precio, Stock
        Text(product.name, Modifier.weight(2f), color = TextoPrincipal, fontSize = 14.sp)
        Text(product.category, Modifier.weight(1f), color = TextoPrincipal, fontSize = 14.sp)
        Text(product.price, Modifier.weight(1f), color = TextoPrincipal, fontSize = 14.sp)
        Text(product.stock.toString(), Modifier.weight(0.7f), color = TextoPrincipal, fontSize = 14.sp)

        // Botón Editar (.btn-editar-producto)
        Button(
            onClick = { /* Acción de editar */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = FondoDark, // background: #222a50 (cercano a FondoDark)
                contentColor = Accent // color: #03ffe2
            ),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Filled.Edit, contentDescription = "Editar", modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(5.dp))
            Text("Editar", fontSize = 14.sp)
        }
    }
}