package com.example.lvl_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lvl_up.LvlUpApplication
import com.example.lvl_up.ui.theme_Admin.*
import com.example.lvl_up.data.User
import com.example.lvl_up.viewmodel.UserViewModel
import com.example.lvl_up.viewmodel.UserViewModelFactory


@Composable
fun UserScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as LvlUpApplication
    val repository = application.userRepository
    val factory = UserViewModelFactory(repository)

    val viewModel: UserViewModel = viewModel(factory = factory)


    val users by viewModel.userListState.collectAsState()

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

            UserListContent(
                users = users,
                navController = navController,
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
            )

            DownbarMenu(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun UserListContent(users: List<User>, navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Usuarios",
                fontSize = 40.sp,
                color = Accent,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )

            Button(
                onClick = {
                    navController.navigate("crearUsuario")
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = FondoPanel
                ),
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.height(40.dp)
            ) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Crear usuario", modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(7.dp))
                Text("Crear usuario", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FondoDark)
            }
        }

        Surface(
            shape = RoundedCornerShape(15.dp),
            color = FondoPanel,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(24.dp)) {
                item { UserTableHeader() }

                items(users) { user ->
                    UserRow(user, navController)
                    Divider(color = TextoSecundario.copy(alpha = 0.3f), thickness = 1.dp)
                }
            }
        }
    }
}


@Composable
fun UserTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(FondoDark, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .padding(vertical = 14.dp),
    ) {
        Text("Usuarios",
            Modifier.weight(0.5f),
            color = Accent, fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center)
    }
}


@Composable
fun UserRow(user: User, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ID
        Text(user.id.toString(),
            Modifier.weight(1.0f),
            color = TextoPrincipal,
            fontSize = 14.sp)

        Column (
            modifier = Modifier
                .weight(4.5f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .size(130.dp) // size: 42px in css + border
                    .background(FondoDark, shape = RoundedCornerShape(50))
                    .border(2.dp, Accent, RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "AVATAR",
                    fontSize = 8.sp,
                    color = TextoSecundario)
            }
            Spacer(Modifier.height(7.dp))

            // Name
            Text(user.nombre,
                modifier = Modifier.fillMaxWidth(),
                color = TextoPrincipal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center)

            // Email
            Text(user.correo,
                modifier = Modifier.fillMaxWidth(),
                color = TextoSecundario,
                fontSize = 11.sp,
                textAlign = TextAlign.Center)

            // Rol
            Text("Rol: ${user.rol}",
                modifier = Modifier.fillMaxWidth(),
                color = TextoSecundario,
                fontSize = 13.sp,
                textAlign = TextAlign.Center)

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = {
                    navController.navigate("edit_user/${user.id}")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent,
                    contentColor = FondoDark
                ),
                shape = CutCornerShape(
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
                modifier = Modifier.fillMaxWidth()

            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Editar", modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(5.dp))
                Text("Editar", fontSize = 16.sp, color = FondoDark)
            }
        }

    }
}


