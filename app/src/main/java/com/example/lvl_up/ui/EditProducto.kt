package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.data.Product
import com.example.lvl_up.ui.theme_Admin.Accent
import com.example.lvl_up.ui.theme_Admin.ErrorColor
import com.example.lvl_up.ui.theme_Admin.FondoGradientEnd
import com.example.lvl_up.ui.theme_Admin.FondoGradientStart
import com.example.lvl_up.ui.theme_Admin.FondoPanel
import com.example.lvl_up.viewmodel.ProductViewModel
import com.example.lvl_up.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun EditProduct(navController: NavController, productId: Int) {
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication
    val repository = application.productRepository
    val factory = ProductViewModelFactory(repository)
    val viewModel: ProductViewModel = viewModel(factory = factory)

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
                .padding(top = 50.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                EditProductForm(navController, viewModel, productId)

                Spacer(modifier = Modifier.height(30.dp))
            }

            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun validatePrice(price: String): Boolean {
    val priceValue = price.replace(",", ".").toDoubleOrNull()
    return priceValue != null && priceValue > 0.0
}

private fun validateStock(stock: String): Boolean {
    val stockValue = stock.toIntOrNull()
    return stockValue != null && stockValue > 0
}

@Composable
fun EditProductForm(navController: NavController, viewModel: ProductViewModel, productId: Int) {
    var originalProduct by remember { mutableStateOf<Product?>(null) }
    var nombre by remember { mutableStateOf("Cargando...") }
    var categoria by remember { mutableStateOf("Cargando...") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    var precioError by remember { mutableStateOf<String?>(null) }
    var stockError by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current
    val (precioFocus, stockFocus) = FocusRequester.createRefs()
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        scope.launch {
            val product = viewModel.getProductForEdit(productId)
            originalProduct = product
            product?.let {
                nombre = it.name
                categoria = it.category
                precio = it.price
                stock = it.stock.toString()
            } ?: run {
                nombre = "Producto no encontrado"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Editar Producto",
            fontSize = 40.sp,
            color = Accent,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = nombre,
            fontSize = 28.sp,
            color = Accent,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = categoria,
            fontSize = 20.sp,
            color = FondoPanel,
            style = MaterialTheme.typography.bodyLarge
        )

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = FondoPanel,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(19.dp)
            ) {


                OutlinedTextField(
                    value = precio,
                    onValueChange = { newValue ->
                        precio = newValue.filter { char -> char.isDigit() || char == '.' || char == ',' }
                        precioError = if (precio.isNotEmpty() && !validatePrice(precio)) {
                            "El precio debe ser mayor que 0."
                        } else null
                    },
                    label = { Text("Precio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(precioFocus),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { stockFocus.requestFocus() }),
                    isError = precioError != null,
                    supportingText = { if (precioError != null) Text(precioError!!, color = ErrorColor) }
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = { newValue ->
                        stock = newValue.filter { char -> char.isDigit() }
                        stockError = if (stock.isNotEmpty() && !validateStock(stock)) {
                            "El stock debe ser un número entero mayor que 0."
                        } else null
                    },
                    label = { Text("Stock") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(stockFocus),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    isError = stockError != null,
                    supportingText = { if (stockError != null) Text(stockError!!, color = ErrorColor) }
                )

                Button(
                    onClick = {
                        precioError = if (!validatePrice(precio)) "El precio debe ser un número mayor que 0." else null
                        stockError = if (!validateStock(stock)) "El stock debe ser un número entero mayor que 0." else null

                        val hasError = precioError != null || stockError != null || originalProduct == null

                        focusManager.clearFocus()

                        if (!hasError && originalProduct != null) {
                            val updatedProduct = originalProduct!!.copy(
                                price = precio,
                                stock = stock.toIntOrNull() ?: originalProduct!!.stock
                            )


                            viewModel.updateProduct(updatedProduct)


                            navController.popBackStack()
                        }
                    },
                    enabled = originalProduct != null && precioError == null && stockError == null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent,
                        contentColor = FondoPanel
                    ),
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 18.dp)
                ) {
                    Text("GUARDAR CAMBIOS", fontSize = 16.sp)
                }
            }
        }
    }
}