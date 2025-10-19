package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lvl_up.ui.theme_Admin.*
import com.example.lvl_up.data.Product
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.viewmodel.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CreateProduct(navController: NavController) {

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
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CreateProductForm(navController, viewModel)

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
fun CreateProductForm(navController: NavController, viewModel: ProductViewModel) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Nuevo Producto",
            fontSize = 40.sp,
            color = Accent,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        var nombre by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }
        var stock by remember { mutableStateOf("") }
        var categoria by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var urlImagen by remember { mutableStateOf("") }

        var nombreError by remember { mutableStateOf<String?>(null) }
        var precioError by remember { mutableStateOf<String?>(null) }
        var stockError by remember { mutableStateOf<String?>(null) }
        var descripcionError by remember { mutableStateOf<String?>(null) }
        val focusManager = LocalFocusManager.current
        val (nombreFocus, precioFocus, stockFocus, descripcionFocus, urlFocus) = FocusRequester.createRefs()

        val categoriasList = listOf("Computadores", "Tarjetas de Video", "Periféricos", "Monitores", "Almacenamiento", "Accesorios")

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

                // --- 1. Nombre del Producto ---
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { newValue ->
                        nombre = newValue
                        nombreError = if (nombre.isBlank()) "El nombre es obligatorio." else null
                    },
                    label = { Text("Nombre del Producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nombreFocus),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { precioFocus.requestFocus() }),
                    isError = nombreError != null,
                    supportingText = { if (nombreError != null) Text(nombreError!!, color = ErrorColor) }
                )

                // --- 2. Categoría (Dropdown) ---
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = {},
                        label = { Text("Categoría") },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Seleccionar Categoría",
                                Modifier.clickable { expanded = true }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        categoriasList.forEach { label ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    categoria = label
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // --- 3. Precio y Stock (Fila Horizontal) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { newValue ->
                            precio = newValue.filter { char -> char.isDigit() || char == '.' }
                            precioError = if (precio.isNotEmpty() && !validatePrice(precio)) {
                                "El precio debe ser mayor que 0."
                            } else null
                        },
                        label = { Text("Precio") },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(precioFocus), // ⬅️ Foco 2
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
                                "El stock debe ser mayor que 0."
                            } else null
                        },
                        label = { Text("Stock") },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(stockFocus),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { descripcionFocus.requestFocus() }),
                        isError = stockError != null,
                        supportingText = { if (stockError != null) Text(stockError!!, color = ErrorColor) }
                    )
                }
                // --- 4. Descripción ---
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                        .focusRequester(descripcionFocus),
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { urlFocus.requestFocus() }),
                    isError = descripcionError != null,
                    supportingText = { if (descripcionError != null) Text(descripcionError!!, color = ErrorColor) }
                )

                // --- 5. Imagen (URL/File) ---
                OutlinedTextField(
                    value = urlImagen,
                    onValueChange = { urlImagen = it },
                    label = { Text("URL/File de la Imagen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(urlFocus),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                )

                Button(
                    onClick = {
                        nombreError = if (nombre.isBlank()) "El nombre es obligatorio." else null
                        precioError = if (!validatePrice(precio)) "El precio debe ser un número mayor que 0." else null
                        stockError = if (!validateStock(stock)) "El stock debe ser un número entero mayor que 0." else null
                        descripcionError = if (descripcion.isBlank()) "La descripción es obligatoria." else null

                        val urlEsValida = urlImagen.isNotBlank()
                        val categoriaEsValida = categoria.isNotBlank()

                        val hasError = nombreError != null || precioError != null || stockError != null || descripcionError != null || !urlEsValida || !categoriaEsValida

                        focusManager.clearFocus()

                        if (!hasError) {
                            val newProduct = Product(
                                id = 0,
                                imageUrl = urlImagen,
                                name = nombre,
                                category = categoria,
                                price = precio,
                                stock = stock.toIntOrNull() ?: 0
                            )

                            viewModel.insertProduct(newProduct)

                            navController.popBackStack()
                        }
                    },
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
                    Text("CREAR PRODUCTO", fontSize = 16.sp)
                }
            }
        }
    }
}