package com.example.lvl_up.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.data.User
import com.example.lvl_up.ui.theme_Tienda.*
import com.example.lvl_up.viewmodel.UserViewModel
import com.example.lvl_up.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

// --- Funciones de Validación (Reutilizadas de CreateUser.kt) ---
private fun validateRutFormat(rut: String): Boolean {
    val rutRegex = Regex("^(\\d{1,2}\\.)?\\d{3}\\.\\d{3}-[0-9kK]$")
    return rutRegex.matches(rut)
}

private fun validateNombreFormat(nombre: String): Boolean {
    if (nombre.length > 40) return false
    val words = nombre.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
    return words.size >= 2 // Acepta nombre y al menos un apellido
}

private fun validatePassword(password: String): Boolean {
    return password.length >= 8
}

private fun validateEmailFormat(email: String): Boolean {
    val generalEmailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    if (!generalEmailRegex.matches(email)) {
        return false
    }
    val allowedDomains = listOf("@gmail.com", "@duocuc.cl")
    return allowedDomains.any { email.endsWith(it, ignoreCase = true) }
}


@Composable
fun RegistroScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication
    val factory = UserViewModelFactory(application.userRepository)
    val viewModel: UserViewModel = viewModel(factory = factory)

    TiendaGamerTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(DarkBackground, BlackBackground)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RegistroForm(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RegistroForm(navController: NavController, viewModel: UserViewModel) {
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var avatarUrl by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var rutError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current
    val (rutFocus, emailFocus, passwordFocus, avatarFocus) = remember { FocusRequester.createRefs() }
    val scope = rememberCoroutineScope()

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = CardSurface,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineLarge,
                color = NeonCyan,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = if (it.isNotEmpty() && !validateNombreFormat(it)) "Debe tener nombre y apellido." else null
                },
                label = { Text("Nombre y Apellido") },
                isError = nombreError != null,
                supportingText = { if (nombreError != null) Text(nombreError!!, color = RedAccent) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { rutFocus.requestFocus() }),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = NeonCyan, unfocusedIndicatorColor = MutedText)
            )

            OutlinedTextField(
                value = rut,
                onValueChange = {
                    rut = it
                    rutError = if (it.isNotEmpty() && !validateRutFormat(it)) "Formato de RUT incorrecto." else null
                },
                label = { Text("RUT (ej: 12.345.678-9)") },
                isError = rutError != null,
                supportingText = { if (rutError != null) Text(rutError!!, color = RedAccent) },
                modifier = Modifier.fillMaxWidth().focusRequester(rutFocus),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() }),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = NeonCyan, unfocusedIndicatorColor = MutedText)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (it.isNotEmpty() && !validateEmailFormat(it)) "Dominio no válido." else null
                },
                label = { Text("Correo Electrónico") },
                isError = emailError != null,
                supportingText = { if (emailError != null) Text(emailError!!, color = RedAccent) },
                modifier = Modifier.fillMaxWidth().focusRequester(emailFocus),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = NeonCyan, unfocusedIndicatorColor = MutedText)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = if (it.isNotEmpty() && !validatePassword(it)) "Mínimo 8 caracteres." else null
                },
                label = { Text("Contraseña") },
                isError = passwordError != null,
                supportingText = { if (passwordError != null) Text(passwordError!!, color = RedAccent) },
                modifier = Modifier.fillMaxWidth().focusRequester(passwordFocus),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { avatarFocus.requestFocus() }),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = NeonCyan, unfocusedIndicatorColor = MutedText)
            )

            OutlinedTextField(
                value = avatarUrl,
                onValueChange = { avatarUrl = it },
                label = { Text("URL de Foto de Perfil (Opcional)") },
                modifier = Modifier.fillMaxWidth().focusRequester(avatarFocus),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = NeonCyan, unfocusedIndicatorColor = MutedText)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    // 1. Validación de formato
                    nombreError = if (!validateNombreFormat(nombre)) "Nombre y apellido son requeridos." else null
                    rutError = if (!validateRutFormat(rut)) "RUT con formato incorrecto o vacío." else null
                    emailError = if (!validateEmailFormat(email)) "Correo con formato o dominio no válido." else null
                    passwordError = if (!validatePassword(password)) "La contraseña debe tener al menos 8 caracteres." else null

                    val hasFormatError = listOf(nombreError, rutError, emailError, passwordError).any { it != null }

                    if (!hasFormatError) {
                        // 2. Si el formato es correcto, validamos duplicados en la BD
                        scope.launch {
                            val isRutDuplicate = viewModel.isRutTaken(rut.trim())
                            if (isRutDuplicate) {
                                rutError = "Este RUT ya está registrado."
                            }

                            val isEmailDuplicate = viewModel.isEmailTaken(email.trim())
                            if (isEmailDuplicate) {
                                emailError = "Este correo ya está en uso."
                            }

                            // 3. Si no hay duplicados, procedemos a registrar
                            if (!isRutDuplicate && !isEmailDuplicate) {
                                val newUser = User(
                                    nombre = nombre.trim(),
                                    rut = rut.trim(),
                                    correo = email.trim(),
                                    contrasena = password.trim(),
                                    rol = "Cliente",
                                    avatarUrl = avatarUrl.trim()
                                )
                                viewModel.insertUser(newUser)
                                navController.popBackStack()
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = DarkButton),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("REGISTRARSE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NeonCyan),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("VOLVER AL LOGIN", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NeonCyan)
            }
        }
    }
}

