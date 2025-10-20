package com.example.lvl_up

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


val NeonCyan = Color(0xFF00FFFF)

data class CategoryItem(val name: String)

@Composable
fun CategoriasScreen(navController: NavController) {


    val categories = listOf(
        CategoryItem("Juegos de mesa"),
        CategoryItem("Accesorios"),
        CategoryItem("Consolas"),
        CategoryItem("Computadoras gamers"),
        CategoryItem("Sillas gamer"),
        CategoryItem("Mousepads"),
        CategoryItem("Poleras y polerones personalizados")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C2D)) // Fondo oscuro
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = "⚡ Categorías ⚡",
                modifier = Modifier.padding(bottom = 16.dp, top = 24.dp),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = NeonCyan,
                    shadow = Shadow(color = NeonCyan.copy(alpha = 0.5f), blurRadius = 10f)
                )
            )
        }

        items(categories) { category ->
            CategoryItemComponent(category = category, navController = navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun CategoryItemComponent(category: CategoryItem, navController: NavController) {
    val darkBlue = Color(0xFF131722)
    val neonBorder = Color(0xFF00FFFF)
    val cardHeight = 48.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(darkBlue)
            .border(1.dp, neonBorder, RoundedCornerShape(8.dp))
            .clickable {
                navController.navigate("product_categories/${category.name}")
            }
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = category.name,
            color = neonBorder,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}