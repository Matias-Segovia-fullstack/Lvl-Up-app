package com.example.lvl_up

// --- SECCIÓN DE IMPORTS CORREGIDA Y EXPLÍCITA ---
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells // <-- ¡ESTA LÍNEA ES NECESARIA!
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
// --- ¡AQUÍ ESTÁ LA LÍNEA MÁGICA! ---
import androidx.compose.material3.Typography // <-- ¡Y ESTA TAMBIÉN!
// ------------------------------------
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lvl_up.ui.theme.*




// (El resto del código es exactamente el mismo que ya teníamos)
@Composable
fun TiendaGamerTheme(content: @Composable () -> Unit) {
    val colorScheme = darkColorScheme(
        primary = NeonCyan,
        secondary = ButtonBlue,
        tertiary = NeonCyan,
        background = DarkBlue,
        surface = CardBackground,
        onPrimary = DarkBlue,
        onSecondary = White,
        onTertiary = White,
        onBackground = White,
        onSurface = White
    )
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography, // Ahora esta Typography SÍ o SÍ es la correcta
        content = content
    )
}

@Composable
fun TiendaScreen(navController: NavController) {
    TiendaGamerTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(DarkBlue, DarkerBlue)
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Navbar(navController) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CultureBanner() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CartButton() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { OffersSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { CatalogBanner() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { NewsSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { SupportButton() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { SocialShare() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { Footer() }
        }
    }
}

// --- Resto de los componentes... ---

@Composable
fun Navbar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Mi Tienda PC Gamer",
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.pixel),
            contentDescription = "Logo",
            modifier = Modifier.size(80.dp)
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Button(
                onClick = { /* Acción de login */ },
                colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(painter = painterResource(id = android.R.drawable.ic_menu_myplaces), contentDescription = "Login", tint = White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Login", color = White)
            }
        }
    }
}

@Composable
fun CultureBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF23293e), Color(0xFF3242a8))
                )
            )
            .border(3.dp, NeonCyan, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_media_play),
            contentDescription = "Controller",
            tint = NeonCyan,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Cultura Gamer:", color = White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Último parche de R6",
            color = NeonCyan,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logorainbo),
            contentDescription = "R6 Logo",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun CartButton() {
    Button(
        onClick = { /* Acción ver carrito */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003cff))
    ) {
        Icon(painter = painterResource(id = android.R.drawable.ic_menu_myplaces), contentDescription = "Carrito")
        Text("Ver carrito", modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun OffersSection() {
    val offerItems = listOf(
        OfferItem("https://m.media-amazon.com/images/I/71ybtvmLmDL.jpg", "HyperX Cloud II", "Sonido envolvente", "$69.990"),
        OfferItem("https://cdnx.jumpseller.com/compuelite/image/62384054/thumb/610/610?1744328062", "PC Gamer Elite", "i7 + 32GB RAM", "$779.990"),
        OfferItem("https://ae-pic-a1.aliexpress-media.com/kf/Sb18da7815a734c44b0ba99cff2bceeb6a.png_640x640.png", "Control Xbox Doom", "Edición limitada", "$54.990"),
        OfferItem("https://tienda.lancenter.cl/674-large_default/g502-heroe.jpg", "Logitech G502 HERO", "Mouse gamer", "$39.990")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF222a50), NeonCyan)
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "⚡ GRANDES OFERTAS ⚡",
            color = NeonCyan,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = LocalTextStyle.current.copy(
                shadow = Shadow(color = NeonCyan, blurRadius = 10f)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(650.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(offerItems) { item ->
                ProductCard(item)
            }
        }
    }
}

data class OfferItem(val imageUrl: String, val title: String, val description: String, val price: String)

@Composable
fun ProductCard(item: OfferItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(item.title, color = MutedText, fontSize = 12.sp)
                Text(item.description, color = White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 14.sp)
                Text(item.price, color = PriceGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Button(
                    onClick = { /* Acción */ },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver", color = DarkBlue, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CatalogBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF252850))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = android.R.drawable.star_on), contentDescription = "Stars", tint = NeonCyan)
        Spacer(modifier = Modifier.width(8.dp))
        Text("¡Revisa nuestro catálogo!", color = White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* Acción */ },
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
        ) {
            Text("Ver", color = DarkBlue)
        }
    }
}

@Composable
fun NewsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Noticias Gamer",
            color = NeonCyan,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column {
                AsyncImage(
                    model = "https://elchapuzasinformatico.com/wp-content/uploads/2019/04/Age-of-Empires-II.jpg",
                    contentDescription = "Retro Games",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Top 5 Juegos Retro", color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Doom (1993)\nHalf-Life (1998)\nStarCraft (1998)...", color = MutedText)
                }
            }
        }
    }
}

@Composable
fun Footer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkerBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sobre Nosotros", color = NeonCyan, fontWeight = FontWeight.Bold)
        Text(
            "En Mi Tienda PC Gamer, compartimos tu pasión por la tecnología...",
            color = MutedText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Contáctanos", color = NeonCyan, fontWeight = FontWeight.Bold)
        Text("soporte@mitiendagamer.com", color = MutedText)
        Text("+56 9 9999 9999", color = MutedText)
        Divider(color = MutedText, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
        Text("© 2025 Mi Tienda PC Gamer", color = MutedText, fontSize = 12.sp)
    }
}


@Composable fun SupportButton() { /* TODO */ }
@Composable fun SocialShare() { /* TODO */ }




