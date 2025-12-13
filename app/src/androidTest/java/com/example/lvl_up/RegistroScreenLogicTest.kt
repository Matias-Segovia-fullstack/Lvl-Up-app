
package com.example.lvl_up

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistroScreenLogicTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testValidacion_NombreInvalido_MuestraError() {


        val botonRegistrarseTexto = "REGISTRARSE"


        val labelCampoNombre = "Nombre y Apellido"

        val textoInputInvalido = "Juan"
        val textoErrorEsperado = "Debe tener nombre y apellido." //

        // --- ACT (Actuar) ---

        //  Navegamos de Login a Registro
        composeTestRule.onNodeWithText(botonRegistrarseTexto).performClick()
        composeTestRule.waitForIdle()

        //  Busca el campo de texto por etiqueta ("Nombre y Apellido")
        composeTestRule.onNodeWithText(labelCampoNombre)
            .assertIsDisplayed()
            .performTextInput(textoInputInvalido) // Escribimos "Juan"


        composeTestRule.waitForIdle()




        composeTestRule.onNodeWithText(textoErrorEsperado).assertIsDisplayed()
    }
}