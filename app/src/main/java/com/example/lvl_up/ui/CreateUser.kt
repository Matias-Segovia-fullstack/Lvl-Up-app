package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.data.User
import com.example.lvl_up.ui.theme_Admin.*
// Se eliminan imports de ProductViewModel y ProductViewModelFactory
import com.example.lvl_up.viewmodel.UserViewModel
import com.example.lvl_up.viewmodel.UserViewModelFactory


@Composable
fun CreateUser(navController: NavController) {

    // 1. INICIALIZACIÓN DEL VIEWMODEL Y REPOSITORIO
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication //
    val repository = application.userRepository //
    val factory = UserViewModelFactory(repository) //
    val viewModel: UserViewModel = viewModel(factory = factory) //

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
            .padding(top = 50.dp)
        ) {

            // Contenido Principal (Formulario scrollable)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CreateUserForm(navController, viewModel)
                Spacer(modifier = Modifier.height(30.dp))
            }

            // Barra Inferior (Reutiliza el composable DownbarMenu de AdminScreen)
            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun CreateUserForm(navController: NavController, viewModel: UserViewModel) {
    // Definición de estados para los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("Cliente") }
    var avatarUrl by remember { mutableStateOf("") }
    val rolesList = listOf("Administrador", "Cliente")


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

                // --- 1. Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth(),
                )

                // --- 2. RUT
                OutlinedTextField(
                    value = rut,
                    onValueChange = { rut = it },
                    label = { Text("RUT") },
                    modifier = Modifier.fillMaxWidth(),
                )

                // --- 3. Correo electrónico
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // --- 4. Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )

                // --- 5. Rol (Dropdown)
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxWidth()
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

                // --- 6. Avatar URL
                OutlinedTextField(
                    value = avatarUrl,
                    onValueChange = { avatarUrl = it },
                    label = { Text("Avatar (URL/File input simulado)") },
                    modifier = Modifier.fillMaxWidth(),
                )


                // --- Botón de Acción ---
                Button(
                    onClick = {
                        val newUser = User(
                            id = 0,
                            nombre = nombre,
                            rut = rut,
                            correo = email,
                            contrasena = password,
                            rol = rol,
                            avatarUrl = avatarUrl
                        )

                        viewModel.insertUser(newUser)

                        // ✅ Navegación de vuelta a la lista de usuarios.
                        // Esto asegura que, si la inserción tiene éxito, vuelvas a la lista.
                        navController.navigate("user") {
                            popUpTo("user") { inclusive = true }
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
                    Text("CREAR USUARIO", fontSize = 16.sp)
                }
            }
        }
    }
}