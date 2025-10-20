package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.data.Product
import com.example.lvl_up.ui.theme_Admin.*
import com.example.lvl_up.viewmodel.ProductViewModel
import com.example.lvl_up.viewmodel.ProductViewModelFactory

@Composable
fun ProductCategoriesScreen(navController: NavController, category: String?) {
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication
    val repository = application.productRepository
    val factory = ProductViewModelFactory(repository)
    val viewModel: ProductViewModel = viewModel(factory = factory)

    val products by viewModel.getProductsByCategory(category ?: "").collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = category ?: "Categoría",
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 28.dp),
                textAlign = TextAlign.Center
            )
            LazyColumn {
                items(products) { product ->
                    ProductCategoryRow(product, navController) // ✅ Usando el nuevo Composable
                }
            }
        }
    }
}

@Composable
fun ProductCategoryRow(product: Product, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ID
        Text(
            product.id.toString(),
            Modifier.weight(1.0f),
            color = TextoPrincipal,
            fontSize = 14.sp
        )

        // Columna de detalles del producto
        Column(
            modifier = Modifier
                .weight(4.5f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen (Placeholder)
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .aspectRatio(1f)
                    .background(FondoDark, shape = RoundedCornerShape(10.dp))
                    .border(2.dp, Accent, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "IMG",
                    fontSize = 10.sp,
                    color = TextoSecundario
                )
            }
            Spacer(Modifier.height(7.dp))

            // Nombre (sin stock ni categoría)
            Text(
                product.name,
                modifier = Modifier.fillMaxWidth(),
                color = TextoPrincipal,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            // Botón "Ver producto"
            Button(
                onClick = {
                    // Navega a una futura pantalla de detalles del producto
                    navController.navigate("product_detail/${product.id}")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = FondoDark
                ),
                shape = CutCornerShape(
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                ),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver producto", fontSize = 16.sp, color = FondoDark)
            }
        }
    }
}