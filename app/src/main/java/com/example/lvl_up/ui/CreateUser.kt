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
import com.example.lvl_up.viewmodel.*


@Composable
fun CreateUser(navController: NavController) {

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
                CreateUserForm(navController, viewModel)
                Spacer(modifier = Modifier.height(30.dp))
            }

            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun validateRutFormat(rut: String): Boolean {
    val rutRegex = Regex("^(\\d{1,2}\\.)?\\d{3}\\.\\d{3}-[0-9kK]$")
    return rutRegex.matches(rut)
}

private fun validateNombreFormat(nombre: String): Boolean {
    if (nombre.length > 40) return false
    val words = nombre.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
    return words.size == 2
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
fun CreateUserForm(navController: NavController, viewModel: UserViewModel) {
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("Cliente") }
    var avatarUrl by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val (nombreFocus, rutFocus, emailFocus, passwordFocus, avatarFocus) = FocusRequester.createRefs()

    val rolesList = listOf("Administrador", "Cliente")

    var nombreError by remember { mutableStateOf<String?>(null) }
    var rutError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }


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

                // --- 1. Nombre ---
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { newValue ->
                        nombre = newValue
                        nombreError = if (nombre.isNotEmpty() && !validateNombreFormat(nombre)) {
                            "Debe tener solo nombre y apellido y no exceder 40 caracteres."
                        } else null
                    },
                    label = { Text("Nombre y Apellido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nombreFocus),
                    isError = nombreError != null,
                    supportingText = {
                        if (nombreError != null) {
                            Text(nombreError!!, color = ErrorColor)
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { rutFocus.requestFocus() })
                )

                // --- 2. RUT
                OutlinedTextField(
                    value = rut,
                    onValueChange = { newValue ->
                        rut = newValue
                        rutError = if (rut.isNotEmpty() && !validateRutFormat(rut)) {
                            "Formato RUT incorrecto (ej: 12.345.678-9)."
                        } else null
                    },
                    label = { Text("RUT") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(rutFocus),
                    isError = rutError != null,
                    supportingText = {
                        if (rutError != null) {
                            Text(rutError!!, color = ErrorColor)
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() })
                )

                // --- 3. Correo electrónico ---
                OutlinedTextField(
                    value = email,
                    onValueChange = { newValue ->
                        email = newValue
                        emailError = if (email.isNotEmpty() && !validateEmailFormat(email)) {
                            "Solo se permiten dominios @gmail.com o @duocuc.cl."
                        } else null
                    },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocus),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocus.requestFocus() }
                    ),
                    isError = emailError != null,
                    supportingText = { if (emailError != null) Text(emailError!!, color = ErrorColor) }
                )

                // --- 4. Contraseña ---
                OutlinedTextField(
                    value = password,
                    onValueChange = { newValue ->
                        password = newValue
                        passwordError = if (password.isNotEmpty() && !validatePassword(password)) {
                            "Mínimo 8 caracteres."
                        } else null
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocus),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { avatarFocus.requestFocus() }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError != null,
                    supportingText = { if (passwordError != null) Text(passwordError!!, color = ErrorColor) }
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

                // --- 6. Avatar URL ---
                OutlinedTextField(
                    value = avatarUrl,
                    onValueChange = { avatarUrl = it },
                    label = { Text("Avatar (URL/File input simulado)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(avatarFocus),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                )

                Button(
                    onClick = {
                        nombreError = if (!validateNombreFormat(nombre)) "Nombre inválido/vacío." else null
                        rutError = if (!validateRutFormat(rut)) "RUT inválido/vacío." else null
                        passwordError = if (!validatePassword(password)) "Contraseña requiere 8+ caracteres." else null
                        emailError = if (!validateEmailFormat(email)) "Email no cumple formato o dominio permitido." else null

                        val hasError = nombreError != null || rutError != null || passwordError != null || emailError != null || email.isBlank()

                        if (!hasError) {
                            val newUser = User(
                                id = 0, nombre = nombre, rut = rut, correo = email,
                                contrasena = password, rol = rol, avatarUrl = avatarUrl
                            )

                            viewModel.insertUser(newUser)

                            focusManager.clearFocus()

                            navController.navigate("user") {
                                popUpTo("user") { inclusive = true }
                            }
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