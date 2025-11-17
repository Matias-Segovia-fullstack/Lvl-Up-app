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
import com.example.lvl_up.data.UserManager
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
import com.example.lvl_up.ui.theme_Tienda.*
import com.example.lvl_up.viewmodel.UserViewModel
import com.example.lvl_up.viewmodel.UserViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.testTag // Test Unitario front end :)

@Composable
fun LoginScreen(navController: NavController) {
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
                LoginForm(navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }
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
                text = "Bienvenido de Nuevo",
                style = MaterialTheme.typography.headlineLarge,
                color = NeonCyan,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    loginError = null
                },
                label = { Text("Correo Electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("email_field"), // <<< CAMBIO 1
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = NeonCyan,
                    unfocusedIndicatorColor = MutedText,
                    cursorColor = NeonCyan
                ),
                enabled = !isLoading
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    loginError = null
                },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester)
                    .testTag("password_field"), // <<< CAMBIO 2
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = NeonCyan,
                    unfocusedIndicatorColor = MutedText,
                    cursorColor = NeonCyan
                ),
                enabled = !isLoading
            )

            // --- CAMBIO 1: Contenedor para la barra Y el error ---
            // Le damos una altura mínima fija para que los botones de abajo no salten
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp) // <<< ALTURA MÍNIMA
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth().height(6.dp), // <<< BARRA MÁS GRUESA
                        color = NeonCyan,
                        trackColor = MutedText.copy(alpha = 0.4f)
                    )
                } else if (loginError != null) {
                    // El error aparece en el mismo espacio
                    Text(
                        text = loginError!!,
                        color = RedAccent,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            // Eliminamos el Spacer(10.dp) que estaba antes del botón

            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (isLoading) return@Button

                    scope.launch {
                        isLoading = true
                        loginError = null

                        val loginJob = async {
                            viewModel.loginUser(email.trim(), password.trim())
                        }

                        val timerJob = launch {
                            delay(2000) // 2 segundos
                        }

                        val user = loginJob.await()
                        timerJob.join()

                        if (user != null) {
                            UserManager.currentUserId = user.id
                            UserManager.currentUserName = user.nombre
                            UserManager.currentUserEmail = user.correo
                            val destination = if (user.rol.equals("Administrador", ignoreCase = true)) {
                                "admin"
                            } else {
                                "tienda"
                            }
                            navController.navigate(destination) {
                                popUpTo(0)
                            }
                        } else {
                            loginError = "Correo o contraseña incorrectos."
                        }

                        isLoading = false
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = DarkButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("login_button"), // <<< CAMBIO 3
                enabled = !isLoading
            ) {
                Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { navController.navigate("registro") },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NeonCyan),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = !isLoading
            ) {
                Text("REGISTRARSE", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NeonCyan)
            }
        }
    }
}