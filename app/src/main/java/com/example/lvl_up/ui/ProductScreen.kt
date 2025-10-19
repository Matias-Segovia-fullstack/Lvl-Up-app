package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lvl_up.ui.theme_Admin.* import com.example.lvl_up.data.Product
// 🛑 Ya no necesitamos sampleProducts aquí
import androidx.compose.runtime.collectAsState // ✅ Importado
import androidx.compose.ui.platform.LocalContext // ✅ Importado
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lvl_up.LvlUpApplication // ✅ Importado
import com.example.lvl_up.viewmodel.ProductViewModel // ✅ Importado
import com.example.lvl_up.viewmodel.ProductViewModelFactory // ✅ Importado
import androidx.compose.material3.Text // Asegurando importaciones
import androidx.compose.runtime.getValue // Necesario para 'by' en collectAsState()


@Composable
fun ProductScreen(navController: NavController) {

    // 🛑 1. CONEXIÓN AL VIEWMODEL USANDO LA FÁBRICA
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication
    val repository = application.productRepository
    val factory = ProductViewModelFactory(repository)

    val viewModel: ProductViewModel = viewModel(factory = factory)

    // 🛑 2. Observar la lista dinámica de productos de la DB
    // Reemplaza la antigua lista estática
    val products by viewModel.productListState.collectAsState()

    // 🛑 3. ELIMINA la línea: val products = sampleProducts

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd)
                )
            )
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
        ) {
            ProductListContent(
                products = products, // ✅ Ahora usa la lista dinámica
                navController = navController,
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
            )

            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )

            // Aquí puedes añadir temporalmente el botón de prueba si lo deseas:
            // TestProductInsertButton(viewModel = viewModel)
        }
    }
}



@Composable
fun ProductListContent(products: List<Product>, navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Productos",
                fontSize = 40.sp,
                color = Accent,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = {
                    navController.navigate("crearProducto")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent, // background: #03ffe2
                    contentColor = FondoPanel // color: #23293e
                ),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Crear producto", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(7.dp))
                Text("Crear producto", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FondoDark)
            }
        }

        // --- Tabla de productos ---
        Surface(
            shape = RoundedCornerShape(15.dp), // border-radius: 15px
            color = FondoPanel, // background: #23293e
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(24.dp)) {
                // Encabetabla (th)
                item { ProductTableHeader() }

                // Filas de los productos (tr)
                items(products) { product ->
                    ProductRow(product, navController)
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
        Text("Productos",
            Modifier.weight(0.5f),
            color = Accent, fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center)
        }
}

// ----------------------------------------------------------------------------------

@Composable
fun ProductRow(product: Product, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ID
        Text(product.id.toString(),
            Modifier.weight(1.0f),
            color = TextoPrincipal,
            fontSize = 14.sp)

        // Imagen (Placeholder que simula .producto-img)
        Column (
            modifier = Modifier
                .weight(4.5f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.Start
        ){
            Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .background(FondoDark, shape = RoundedCornerShape(10.dp))
                .border(2.dp, Accent, RoundedCornerShape(10.dp))

        ) {
            Text(text = "IMG",
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.Center),
                color = TextoSecundario)
        }
            Spacer(Modifier.height(7.dp))

            Text(product.name,
                modifier = Modifier.fillMaxWidth(),
                color = TextoPrincipal,
                fontSize = 15.sp,
                textAlign = TextAlign.Center)

            Text("Stock: ${product.stock}",
                modifier = Modifier.fillMaxWidth(),
                color = TextoPrincipal,
                fontSize = 13.sp,
                textAlign = TextAlign.Center)

            Button(
                onClick = {
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = FondoDark
                ),
                shape = CutCornerShape(
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
                modifier = Modifier.fillMaxWidth()

            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Editar", modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(5.dp))
                Text("Editar", fontSize = 16.sp, color = FondoDark)
            }
        }

    }
}

//este es un comentario