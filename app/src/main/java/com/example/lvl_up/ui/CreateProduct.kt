package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions // Necesario para la configuración del teclado
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType // Necesario para el tipo de teclado
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lvl_up.ui.theme_Admin.*


@Composable
fun CreateProduct(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(FondoGradientStart, FondoGradientEnd)
                )
            )
    ) {
        // Contenedor principal para apilar el contenido y la barra inferior
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp) // MARGEN SUPERIOR APLICADO
        ) {

            Column(
                modifier = Modifier
                    .weight(1f) // Peso 1f para ocupar el espacio restante
                    .padding(horizontal = 24.dp) // Padding lateral
                    .verticalScroll(rememberScrollState())
            ) {
                CreateProductForm(navController)

                // Espacio extra al final del scroll
                Spacer(modifier = Modifier.height(30.dp))
            }

            // 2. Barra Inferior (SidebarMenu)
            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CreateProductForm(navController: NavController) {
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

        // Definición de estados para los campos del formulario
        var nombre by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }
        var stock by remember { mutableStateOf("") }
        var categoria by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var urlImagen by remember { mutableStateOf("") }


        val categoriasList = listOf("Computadores", "Tarjetas de Video", "Periféricos", "Monitores", "Almacenamiento", "Accesorios")

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = FondoPanel,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // Contenedor principal: Column para la pila vertical
            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(19.dp) // Espaciado vertical entre campos
            ) {

                // --- 1. Nombre del Producto ---
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto") },
                    modifier = Modifier.fillMaxWidth()
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
                    // Campo Precio
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it.filter { char -> char.isDigit() || char == '.' } },
                        label = { Text("Precio") },
                        modifier = Modifier.weight(1f),
                        // ⬅️ SOLUCIÓN: Teclado numérico decimal para el precio
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    // Campo Stock
                    OutlinedTextField(
                        value = stock,
                        onValueChange = { stock = it.filter { char -> char.isDigit() } },
                        label = { Text("Stock") },
                        modifier = Modifier.weight(1f),
                        // ⬅️ SOLUCIÓN: Teclado numérico estándar para el stock
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                // --- 4. Descripción ---
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp),
                    maxLines = 5,
                )

                // --- 5. Imagen (URL/File) ---
                OutlinedTextField(
                    value = urlImagen,
                    onValueChange = { urlImagen = it },
                    label = { Text("URL/File de la Imagen") },
                    modifier = Modifier.fillMaxWidth(),
                )


                // --- Botón de Acción ---
                Button(
                    onClick = {  }, // Navegar de vuelta a la lista
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