package com.example.lvl_up

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lvl_up.ui.MainNav
import com.example.lvl_up.ui.theme_Admin.AdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminTheme {

                MainNav()
            }
        }
    }
}
