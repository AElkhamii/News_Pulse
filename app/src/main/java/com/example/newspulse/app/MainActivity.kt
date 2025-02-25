package com.example.newspulse.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsPulseTheme {
                val navController = rememberNavController()
                NewsPulseNavigationRoot(navController = navController)
            }
        }
    }
}
