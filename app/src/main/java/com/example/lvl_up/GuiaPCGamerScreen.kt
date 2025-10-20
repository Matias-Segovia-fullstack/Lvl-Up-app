package com.example.lvl_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// CLASE DE DATOS PARA LOS CONSEJOS DE LA GUÍA
data class TipItem(
    val title: String,
    val description: String,
    val imageResId: Int,
    val rank: Int
)

// =================================================================
// PANTALLA PRINCIPAL
// =================================================================

@Composable
fun PCGamerGuideScreen(navController: NavController) {
    // Definición de los 6 consejos
    val tipItems = listOf(
        TipItem("Define tu presupuesto", "Establecer un presupuesto realista entre de comienzo. Esto te ayudará a tomar decisiones sobre qué componentes priorizar y evitar gastos innecesarios.", R.drawable.img_4, 1),
        TipItem("Verifica compatibilidad", "Asegúrate de que todos los componentes sean compatibles entre sí. Presta especial atención al socket de la CPU con la placa base y la RAM compatible.", R.drawable.img_5, 2),
        TipItem("Invierte en una buena GPU", "La tarjeta gráfica es crucial para gaming. Asigna una parte significativa de tu presupuesto a una GPU que satisfaga tus necesidades de rendimiento.", R.drawable.img_6, 3),
        TipItem("No escatimes en la fuente de poder", "Una fuente de alimentación de calidad protege tus componentes de fluctuaciones de energía. Elige una con certificación 80 Plus y suficiente potencia.", R.drawable.img_7, 4),
        TipItem("Sistema de refrigeración adecuado", "Mantén tus componentes frescos con un buen sistema de refrigeración. Considera ventiladores adicionales o refrigeración líquida según tus necesidades.", R.drawable.img_8, 5),
        TipItem("Incluye un SSD en tu build", "Un SSD mejorará significativamente los tiempos de carga de sistema y juegos. Idealmente, usa un SSD para el sistema operativo y un HDD para almacenamiento.", R.drawable.img_9, 6)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C2D)) // Fondo azul oscuro similar al diseño
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        item {
            Text(
                "Consejos para Armar tu PC Gamer",
                color = Color(0xFF00FFFF), // Cian neón
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
        }

        // Subtítulo
        item {
            Text(
                "Aprende cómo construir la máquina de tus sueños con esta guía esencial para principiantes y expertos.",
                color = Color.LightGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp) // Aplica el padding horizontal primero
                    .padding(bottom = 32.dp)     // Aplica el padding inferior después
            )
        }

        // Lista de Consejos (Las Tarjetas)
        items(tipItems) { tip ->
            TipCard(tip)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Footer de derechos de autor
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "© 2025 GameLegend. Todos los derechos reservados.\nConsejos expertos para armar el PC gamer perfecto para tus necesidades.",
                color = Color.Gray,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// =================================================================
// COMPONENTE DE TARJETA INDIVIDUAL
// =================================================================

@Composable
fun TipCard(tip: TipItem) {
    val cardColor = Color(0xFF1E1E4C) // Azul/morado oscuro
    val borderColor = Color(0xFF6A0DAD) // Morado brillante/neón

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(RoundedCornerShape(8.dp))
            .background(cardColor)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp)) // Borde morado neón
    ) {
        // 1. Imagen y Rank/Número
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Altura fija para la imagen
        ) {
            // Imagen del componente
            Image(
                painter = painterResource(id = tip.imageResId),
                contentDescription = tip.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Rank/Número (Cuadrado Morado en la esquina)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(bottomStart = 8.dp))
                    .background(borderColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = tip.rank.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
        }

        // 2. Contenido de Texto
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            // Título
            Text(
                text = tip.title,
                color = Color(0xFF00FFFF), // Cian neón
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Descripción
            Text(
                text = tip.description,
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}