package com.example.lvl_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lvl_up.ui.theme_Tienda.*



// =================================================================
// CLASES DE DATOS (CORREGIDAS)
// =================================================================
data class OfferItem(val imageResId: Int, val title: String, val description: String, val price: String, val oldPrice: String)
data class NewsItem(val title: String, val summary: String, val imageResId: Int)


// =================================================================
// PANTALLA PRINCIPAL
// =================================================================
@Composable
fun TiendaScreen(navController: NavController) {
    TiendaGamerTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        // Usa los colores definidos en Color.kt a través del ColorScheme
                        colors = listOf(MaterialTheme.colorScheme.background, Color.Black.copy(alpha = 0.9f))
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Navbar(navController) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CultureBanner() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CartButton(navController) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { OffersSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { CatalogBanner(navController) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            // ✅ MODIFICACIÓN 1: Pasar navController a NewsSection
            item { NewsSection(navController) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { Footer() }
        }
    }
}

// =================================================================
// COMPONENTES DE UI
// =================================================================

@Composable
fun Navbar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavbarDark) // Usa el color del navbar
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Título con acentos de color
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Red)) { append("⚡") }
                append("Mi Tienda ")
                withStyle(style = SpanStyle(color = NeonCyan)) { append("PC gamer") }
            },
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium, // Usando un estilo de Type.kt
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Simulación de Logo
        Image(
            painter = painterResource(id = R.drawable.pixel),
            contentDescription = "Logo",
            modifier = Modifier.size(70.dp).offset(x = (-30).dp)
        )

        // Botón de Login
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Button(
                onClick = { /* Acción de login */ },
                colors = ButtonDefaults.buttonColors(containerColor = DarkButton),
                shape = MaterialTheme.shapes.small, // Usa la forma de Shape.kt
                modifier = Modifier.height(38.dp)
            ) {
                // Usar un ícono genérico para simular bi-person
                Icon(painter = painterResource(id = R.drawable.ic_person), contentDescription = "Login", tint = Color.White)
                Text("Login", color = Color.White)
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
            .clip(MaterialTheme.shapes.medium)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(MaterialTheme.colorScheme.surface, CultureBannerEndBlue)
                )
            )
            .border(3.dp, NeonCyan, MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Usar un ícono genérico para simular bi-controller
        Icon(
            painter = painterResource(id = R.drawable.ic_controller),
            contentDescription = "Controller",
            tint = NeonCyan,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("Cultura Gamer:", color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                text = "Revisa el último parche de Rainbow Six siege",
                color = NeonCyan,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Simulación de Imagen
        Image(
            painter = painterResource(id = R.drawable.rainbow_six_symbol),
            contentDescription = "R6 Logo",
            modifier = Modifier
                .size(54.dp)
                .clip(MaterialTheme.shapes.extraSmall)
        )
    }
}

@Composable
fun CartButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("admin") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary) // AccentBlue
    ) {
        // Usar un ícono genérico para simular 🛒
        //Icon(painter = painterResource(id = R.drawable.ic_shopping_cart), contentDescription = "Carrito")
        Spacer(modifier = Modifier.width(8.dp))
        Text("🛒 Ver carrito", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun OffersSection() {


    val offerItems = listOf(
        OfferItem(imageResId = R.drawable.audifonos, title = "Auriculares HyperX Cloud II", description = "sonido envolvente y máxima comodidad", price = "$69.990", oldPrice = "$79.990"),
        OfferItem(imageResId = R.drawable.pcgamer, title = "PC Gamer Elite", description = "Intel Core i7 + 32GB RAM tarjeta 4090", price = "$779.990", oldPrice = "$899.990"),
        OfferItem(imageResId = R.drawable.controldoom, title = "Control Xbox Series X", description = "Control Xbox Series X Inalámbrico edición Doom Eternal", price = "$54.990", oldPrice = "$59.990"),
        OfferItem(imageResId = R.drawable.mousee, title = "Mouse Gamer Logitech G502 HERO", description = "Mouse Gamer Logitech G502 HERO", price = "$39.990", oldPrice = "$49.990")
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(MaterialTheme.shapes.large)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(DarkButton, NeonCyan)
                )
            )
            .border(2.dp, NeonCyan, MaterialTheme.shapes.large)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Código corregido para OffersSection
        Text(
            text = "⚡ GRANDES OFERTAS ⚡",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = NeonCyan, // Establece el color Neón Cian
                shadow = Shadow(color = NeonCyan.copy(alpha = 0.5f), blurRadius = 10f) // Aplica el efecto de sombra
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.heightIn(max = 700.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(offerItems) { item ->
                ProductCard(item)
            }
        }
    }
}

@Composable
fun ProductCard(item: OfferItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = DarkButton),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Placeholder para la imagen del producto
            Image(

                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.White)
                    .clip(MaterialTheme.shapes.small)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(item.title, color = MutedText, fontSize = 12.sp, textAlign = TextAlign.Center)
                Text(item.description, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(4.dp))

                // Precio Tachado
                Text(
                    text = item.oldPrice,
                    color = MutedText,
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.LineThrough
                )

                // Precio de Oferta
                Text(
                    text = item.price,
                    color = PriceSuccess, // Color verde/éxito
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Botón "Agregar al carrito"
                Button(
                    onClick = { /* Acción de agregar al carrito */ },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth().height(36.dp)
                ) {
                    Text("Agregar al carrito", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                }

                // Botón "Ver detalles"
                Button(
                    onClick = { /* Acción Ver detalles */ },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkButton),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth().height(36.dp)
                ) {
                    Text("Ver detalles", color = NeonCyan)
                }
            }
        }
    }
}

@Composable
fun CatalogBanner(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(2.dp, NeonCyan, MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Usar un ícono genérico para simular bi-stars
        Icon(painter = painterResource(id = R.drawable.ic_stars), contentDescription = "Stars", tint = NeonCyan, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = buildAnnotatedString {
                append("¡Revisa todo nuestro catálogo ")
                withStyle(style = SpanStyle(color = NeonCyan)) { append("aquí") }
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Button(
            // 🎯 MODIFICACIÓN: Añadir la navegación al NavController
            onClick = { navController.navigate("catalog") },
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
            shape = MaterialTheme.shapes.small
        ) {
            Text("Ver catálogo", color = DarkButton, fontWeight = FontWeight.Bold)
        }
    }
}

// =================================================================
// COMPONENTES DE NOTICIAS (MODIFICADOS PARA NAVEGACIÓN)
// =================================================================

@Composable
// ✅ MODIFICACIÓN 2: Recibe navController
fun NewsSection(navController: NavController) {
    val newsItems = listOf(
        NewsItem("🎮 Top 5 Mejores Juegos Retro de PC", "Revive la nostalgia gamer con estos clásicos inolvidables...", R.drawable.age),
        NewsItem("💻 Guía para Elegir tu PC Gamer Ideal", "¿Estás pensando en comprar una computadora gamer? Aquí tienes algunos consejos clave...", R.drawable.pcbuild)
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Noticias Gamer",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium.copy(
                color = NeonCyan, // Se asegura que el color sea NeonCyan
                shadow = Shadow(color = NeonCyan.copy(alpha = 0.5f), blurRadius = 8f)
            )
        )


        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            newsItems.forEach { item ->
                // ✅ MODIFICACIÓN 3: Pasa navController a NewsCard
                NewsCard(item, Modifier.weight(1f), navController)
            }
        }
    }
}

@Composable
// ✅ MODIFICACIÓN 4: Recibe navController
fun NewsCard(item: NewsItem, modifier: Modifier = Modifier, navController: NavController) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = DarkButton),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            // Placeholder para la imagen de noticias
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(item.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                Text(item.summary, color = MutedText, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
                // Dentro de la función NewsCard en TiendaScreen.kt:

                Button(
                    onClick = {
                        // LÓGICA DE NAVEGACIÓN MEJORADA
                        if (item.title.contains("Top 5 Mejores Juegos Retro de PC")) {
                            // Si es la noticia Retro, navega a su detalle
                            navController.navigate("retroGamesDetail")
                        } else if (item.title.contains("Guía para Elegir tu PC Gamer Ideal")) {
                            // ✅ Si es la noticia de la Guía, navega a su detalle
                            navController.navigate("pcGamerGuide")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Leer más", color = DarkButton)
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
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // El contenedor principal ahora es una Column en lugar de un Row.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Añadimos un padding general para que no esté pegado a los bordes
            verticalArrangement = Arrangement.spacedBy(24.dp) // Añade un espacio vertical de 24.dp entre las dos secciones
        ) {
            // Sobre Nosotros (Se mantiene como una Column interna)
            // Se elimina el .weight(1f) porque ya no compite por el espacio horizontal.
            Column(Modifier.fillMaxWidth()) {
                Text("Sobre Nosotros", color = NeonCyan, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    "En Mi Tienda PC Gamer, compartimos tu pasión por la tecnología y el mundo gamer. Nuestra visión es ser líderes en la venta de computadoras de alto rendimiento, accesorios gamer y componentes de última generación.",
                    color = MutedText,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp)
                )
            }

            // Contáctanos (Se mantiene como una Column interna)
            // Se elimina el .weight(0.5f).
            Column(Modifier.fillMaxWidth()) {
                Text("Contáctanos", color = NeonCyan, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                Text("soporte@mitiendagamer.com", color = MutedText, fontSize = 14.sp)
                Text("+56 9 7265 9317", color = MutedText, fontSize = 14.sp)
                Text("Santiago, Chile", color = MutedText, fontSize = 14.sp)
            }
        }

        Divider(color = MutedText.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

        Text("© 2025 Mi Tienda PC Gamer. Todos los derechos reservados.", color = MutedText, fontSize = 12.sp)
    }
}