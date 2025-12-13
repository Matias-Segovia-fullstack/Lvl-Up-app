// Archivo: app/src/androidTest/java/com/example/lvl_up/NavigationTest.kt
package com.example.lvl_up

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavegacion_Login_A_Registro() {


        val botonRegistrarseTexto = "REGISTRARSE"

        val tituloRegistroTexto = "Crear Cuenta"

        composeTestRule.onNodeWithText(botonRegistrarseTexto)
            .assertIsDisplayed()
            .performClick()


        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(tituloRegistroTexto).assertIsDisplayed()
    }
}