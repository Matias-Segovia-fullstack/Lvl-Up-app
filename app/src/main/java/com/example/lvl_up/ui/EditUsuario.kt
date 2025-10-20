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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.example.lvl_up.viewmodel.UserViewModel
import com.example.lvl_up.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

// Reutilizamos la función de validación de contraseña de CreateUser
private fun validatePassword(password: String): Boolean {
    return password.length >= 8
}

@Composable
fun EditUser(navController: NavController, userId: Int) { // ⬅️ Recibe el ID del usuario

    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication
    val repository = application.userRepository
    val factory = UserViewModelFactory(repository)
    val viewModel: UserViewModel = viewModel(factory = factory)

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
                EditUserForm(navController, viewModel, userId) // ⬅️ Pasa el ID

                Spacer(modifier = Modifier.height(30.dp))
            }

            // Asumo que DownbarMenu es tu barra de navegación inferior
            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun EditUserForm(navController: NavController, viewModel: UserViewModel, userId: Int) {
    // 1. Estados para el usuario original y los campos editables
    var originalUser by remember { mutableStateOf<User?>(null) }
    var nombre by remember { mutableStateOf("Cargando...") }
    var correo by remember { mutableStateOf("Cargando...") }
    var password by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("Cliente") }

    // 2. Estados para errores
    var passwordError by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current
    val (passwordFocus) = FocusRequester.createRefs()
    val scope = rememberCoroutineScope()
    val rolesList = listOf("Administrador", "Cliente")

    // 3. Cargar datos del usuario al iniciar el componente
    LaunchedEffect(userId) {
        scope.launch {
            val user = viewModel.getUserForEdit(userId)
            originalUser = user
            user?.let {
                nombre = it.nombre
                correo = it.correo
                // NOTA: No cargamos la contraseña original en 'password' por seguridad/diseño
                // Si el usuario deja el campo vacío, se usará la contraseña original.
                rol = it.rol
            } ?: run {
                nombre = "Usuario no encontrado"
                correo = "Error de carga"
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
            text = "Editar Usuario",
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
                horizontalAlignment = Alignment.Start, // Alineamos a la izquierda para los datos de usuario
                verticalArrangement = Arrangement.spacedBy(19.dp)
            ) {

                // --- 1. Información de Usuario (Nombre y Correo - NO EDITABLES) ---
                Text(
                    text = "Nombre:",
                    color = Accent,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = nombre,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Correo:",
                    color = Accent,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = correo,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Accent.copy(alpha = 0.5f))


                // --- 2. Rol (Dropdown) - EDITABLE ---
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

                // --- 3. Contraseña (EDITABLE) ---
                OutlinedTextField(
                    value = password,
                    onValueChange = { newValue ->
                        password = newValue
                        // Solo valida si el campo NO está vacío (si está vacío, se asume la original)
                        passwordError = if (password.isNotEmpty() && !validatePassword(password)) {
                            "Mínimo 8 caracteres."
                        } else null
                    },
                    label = { Text("Nueva Contraseña (Dejar vacío para mantener la actual)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocus),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError != null,
                    supportingText = { if (passwordError != null) Text(passwordError!!, color = ErrorColor) }
                )


                Button(
                    onClick = {
                        passwordError = if (password.isNotEmpty() && !validatePassword(password)) "Contraseña requiere 8+ caracteres." else null
                        val hasError = passwordError != null || originalUser == null

                        focusManager.clearFocus()

                        if (!hasError && originalUser != null) {
                            val newPassword = if (password.isEmpty()) {
                                originalUser!!.contrasena // Mantiene la contraseña original si el campo está vacío
                            } else {
                                password // Usa la nueva contraseña validada
                            }

                            val updatedUser = originalUser!!.copy(
                                contrasena = newPassword,
                                rol = rol
                                // Los demás campos (nombre, rut, correo, etc.) se mantienen igual
                            )

                            // 4. Llamada para actualizar el usuario
                            viewModel.updateUser(updatedUser)

                            // 5. Navegación
                            navController.popBackStack()
                        }
                    },
                    enabled = originalUser != null && passwordError == null,
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