package com.example.lvl_up

import androidx.compose.ui.test.assertIsDisplayed
// <<< CAMBIO 1: Importamos la regla de Activity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
// (Ya no necesitamos NavController ni LoginScreen aquí)
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    // <<< CAMBIO 2: Usamos la regla que lanza tu MainActivity
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginScreen_RenderizaElementosPrincipales() {

        // <<< CAMBIO 3: BORRAMOS el bloque 'composeTestRule.setContent { ... }'
        // Ya no es necesario, porque la Activity se lanza sola
        // y carga el LoginScreen automáticamente.

        // 3. Act & Assert (Actuar y Afirmar)
        // La prueba ahora espera a que la app cargue y luego busca los elementos.

        composeTestRule
            .onNodeWithTag("email_field")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("password_field")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag("login_button")
            .assertIsDisplayed()
    }
}