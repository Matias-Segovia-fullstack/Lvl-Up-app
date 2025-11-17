package com.example.lvl_up

import androidx.compose.ui.test.assertIsDisplayed

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginScreen_RenderizaElementosPrincipales() {

       

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