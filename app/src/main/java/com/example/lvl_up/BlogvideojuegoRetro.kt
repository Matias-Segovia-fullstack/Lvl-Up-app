package com.example.lvl_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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

// CLASE DE DATOS PARA LOS JUEGOS RETRO
data class RetroGame(
    val title: String,
    val year: String,
    val description: String,
    val imageResId: Int,
    val rank: Int
)

// =================================================================
// PANTALLA PRINCIPAL
// =================================================================

@Composable
fun RetroGamesScreen(navController: NavController) {
    // Definición de los datos de los 5 juegos
    val retroGames = listOf(
        RetroGame("Doom", "1993", "El caos y la acción desenfrenada toman forma en este FPS pionero. Con una jugabilidad visceral y adictiva, Doom no solo popularizó el género de disparos en primera persona, sino que se convirtió en un fenómeno cultural imparable.", R.drawable.img, 1),
        RetroGame("Half-Life", "1998", "Más que un simple shooter, Half-Life es una experiencia narrativa magistral. Siguiendo a la deriva a Gordon Freeman, revolucionó la narrativa en los videojuegos al sumergir al jugador en un mundo creíble sin recurrir a cutscenes.", R.drawable.img_1, 2),
        RetroGame("StarCraft", "1998", "La estrategia en tiempo real alcanzó su cenit con este título. Sus tres facciones y su equilibrio son perfectos, creó un ecosistema competitivo legendario que dominó las competiciones y solidificó a Corea del Sur como la meca del gaming profesional.", R.drawable.img_2, 3),
        RetroGame("Diablo II", "2000", "Adéntrate en un mundo gótico de pesadilla y demonios. Diablo II redefinió el ARPG, con un sistema de loot increíblemente gratificante y una atmósfera oscura e inquietante.", R.drawable.img_3, 4),
        RetroGame("Age of Empires II", "1999", "Un viaje épico a través de la historia. Desde la Edad Media hasta el Renacimiento, este juego de estrategia combina una jugabilidad profunda, una campaña histórica educativa y una longevidad envidiable.", R.drawable.age, 5)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C2D)) // Fondo azul oscuro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        item {
            Text(
                "5 Leyendas de los Videojuegos",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
        }

        // Subtítulo
        item {
            Text(
                text = "Descubre los títulos que no solo definieron una era, sino que sentaron las bases de lo que hoy conocemos y amamos en la industria.",
                color = Color.LightGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 32.dp
                )
            )
        }

        // Lista de Juegos (Las Tarjetas)
        items(retroGames) { game ->
            RetroGameCard(game)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Footer de derechos de autor
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "© 2025 levelup. Todos los derechos reservados.\nFotos y descripciones no son solo juego, son la herencia que todo jugador tiene que conocer.",
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
fun RetroGameCard(game: RetroGame) {
    val cardColor = Color(0xFF1E1E4C) // Un azul/morado oscuro similar al de la imagen
    val borderColor = Color(0xFF6A0DAD) // Morado brillante/neón

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f) // Hace la tarjeta un poco más estrecha que la pantalla
            .clip(RoundedCornerShape(8.dp))
            .background(cardColor)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp)) // Borde morado neón
            .padding(bottom = 16.dp)
    ) {
        // 1. Imagen y Rank/Número
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Altura fija para la imagen
        ) {
            // Imagen del Juego
            Image(
                painter = painterResource(id = game.imageResId),
                contentDescription = game.title,
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
                    text = game.rank.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
        }

        // 2. Contenido de Texto
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            // Título
            Text(
                text = game.title,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Año
            Text(
                text = game.year,
                color = Color(0xFF00FFFF), // Un Cian neón para el año
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Descripción
            Text(
                text = game.description,
                color = Color.LightGray,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}
