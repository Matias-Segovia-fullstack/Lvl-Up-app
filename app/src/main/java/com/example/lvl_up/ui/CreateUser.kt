package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lvl_up.ui.theme_Admin.*


@Composable
fun CreateUser(navController: NavController) {
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
            .padding(top = 50.dp) // ⬅️ MARGEN SUPERIOR APLICADO
        ) {

            // 1. Contenido Principal (ocupa todo el espacio restante verticalmente)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                // Título y Botón Volver (crear-usuario-header del HTML)

                // Formulario
                CreateUserForm(navController)

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
    fun CreateUserForm(navController: NavController) {
    Column (modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Nuevo Usuario",
            fontSize = 40.sp,
            color = Accent,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )


        // Definición de estados para los campos del formulario
        var nombre by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var rol by remember { mutableStateOf("Cliente") }
        var avatarUrl by remember { mutableStateOf("") } // Simula el input type="file"

        // Roles para el Dropdown
        val rolesList = listOf("Administrador", "Cliente")

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

                // --- 1. Nombre y Email (form-row) ---
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth(), // ⬅️ Ocupa todo el ancho
                )

                // --- 2. Correo electrónico (STACKED) ---
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(), // ⬅️ Ocupa todo el ancho
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // --- 2. Contraseña y Rol (form-row) ---
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(), // ⬅️ Ocupa todo el ancho
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )

                // --- 4. Rol (STACKED - Eliminado de Row) ---
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxWidth() // ⬅️ Ocupa todo el ancho
                ) {
                    OutlinedTextField(
                        value = rol,
                        onValueChange = {},
                        label = { Text("Rol") },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Seleccionar Rol",
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
                        rolesList.forEach { label ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    rol = label
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // --- 3. Avatar (form-group-full) ---
                OutlinedTextField(
                    value = avatarUrl,
                    onValueChange = { avatarUrl = it },
                    label = { Text("Avatar (URL/File input simulado)") },
                    modifier = Modifier.fillMaxWidth(),
                )


                // --- Botón de Acción (.btn-crear-usuario-form) ---
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
                    Text("CREAR USUARIO", fontSize = 16.sp)
                }
            }
        }
    }
}