package com.example.lvl_up.ui

// <<< CAMBIO: Imports necesarios para la cámara
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PhotoCamera // Icono de cámara
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap // Para mostrar el Bitmap
import androidx.compose.ui.layout.ContentScale // Para escalar la imagen
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
import androidx.core.content.ContextCompat // Para revisar permisos
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.draw.clip // <<< ARREGLO: Import para .clip()
import androidx.compose.foundation.BorderStroke // <<< ARREGLO: Import para BorderStroke


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
    val priceValue = price.toIntOrNull()
    return priceValue != null && priceValue > 0
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

        // <<< CAMBIO 1: Estado para la URL (texto) y el Bitmap (vista previa)
        var urlImagen by remember { mutableStateOf("") }
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }
        val context = LocalContext.current
        // ---

        var nombreError by remember { mutableStateOf<String?>(null) }
        var precioError by remember { mutableStateOf<String?>(null) }
        var stockError by remember { mutableStateOf<String?>(null) }
        var descripcionError by remember { mutableStateOf<String?>(null) }
        val focusManager = LocalFocusManager.current
        val (nombreFocus, precioFocus, stockFocus, descripcionFocus, urlFocus) = FocusRequester.createRefs()

        val categoriasList = listOf("Juegos de mesa", "Accesorios", "Consolas", "Computadoras gamers", "Sillas gamer", "Mousepads", "Poleras y polerones personalizados")

        // <<< CAMBIO 2: Launcher para la cámara (pide un Bitmap)
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { newBitmap: Bitmap? ->
            bitmap = newBitmap // Actualiza la vista previa
            // Simulamos un nombre de archivo. En un caso real, usarías una URI
            if (newBitmap != null) {
                // Llenamos urlImagen con un texto para que pase la validación
                urlImagen = "foto_${System.currentTimeMillis()}.jpg"
            }
        }

        // <<< CAMBIO 3: Launcher para pedir el permiso de CÁMARA
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                cameraLauncher.launch() // Si nos dan permiso, abre la cámara
            }
            // (Opcional: puedes poner un 'else' para notificar al usuario que el permiso fue denegado)
        }
        // ---

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
                            precio = newValue.filter { char -> char.isDigit() } // <-- SOLO DÍGITOS
                            precioError = if (precio.isNotEmpty() && !validatePrice(precio)) {
                                "El precio debe ser un número mayor que 0."
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
                    keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }), // Cambiado a Done
                    isError = descripcionError != null,
                    supportingText = { if (descripcionError != null) Text(descripcionError!!, color = ErrorColor) }
                )

                // --- 5. Imagen (URL/File) ---
                // <<< CAMBIO 4: Reemplazamos el TextField por la vista previa y el botón

                // Vista previa de la imagen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(FondoDark.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .border(1.dp, Accent.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Si hay un bitmap (foto tomada), muéstralo
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Foto del producto",
                            contentScale = ContentScale.Crop, // Escala la imagen para que llene el box
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)) // Redondea la imagen
                        )
                    } ?: Text("Vista Previa de la Imagen", color = TextoSecundario) // Texto si no hay foto
                }

                // Botón para tomar foto
                OutlinedButton( // Cambiado a OutlinedButton para que combine mejor
                    onClick = {
                        // Lógica del botón de la cámara
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) -> {
                                // Si ya tenemos permiso, abre la cámara
                                cameraLauncher.launch()
                            }
                            else -> {
                                // Si no, pide permiso
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    border = BorderStroke(1.dp, Accent) // Borde color Accent
                ) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = "Cámara", tint = Accent)
                    Spacer(Modifier.width(8.dp))
                    Text("Tomar Foto del Producto", color = Accent)
                }
                // --- Fin del CAMBIO 4 ---


                Button(
                    onClick = {
                        nombreError = if (nombre.isBlank()) "El nombre es obligatorio." else null
                        precioError = if (!validatePrice(precio)) "El precio debe ser un número mayor que 0." else null
                        stockError = if (!validateStock(stock)) "El stock debe ser un número entero mayor que 0." else null
                        descripcionError = if (descripcion.isBlank()) "La descripción es obligatoria." else null

                        // <<< CAMBIO 5: Validamos que urlImagen no esté vacío (se llena al tomar la foto)
                        val urlEsValida = urlImagen.isNotBlank()
                        val categoriaEsValida = categoria.isNotBlank()

                        // (Opcional) Error si no hay foto
                        if (!urlEsValida) {
                            // (Puedes añadir un 'urlImagenError' si quieres)
                        }

                        val hasError = nombreError != null || precioError != null || stockError != null || descripcionError != null || !urlEsValida || !categoriaEsValida

                        focusManager.clearFocus()

                        if (!hasError) {
                            val newProduct = Product(
                                id = 0,
                                imageUrl = urlImagen, // <<< CAMBIO 6: Se guarda el nombre simulado
                                name = nombre,
                                category = categoria,
                                price = precio.toIntOrNull() ?: 0,
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