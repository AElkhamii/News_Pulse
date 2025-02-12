package com.example.newspulse.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsPulseTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = {BottomNavigationBar(navController)}) { innerPadding ->
                    NewsPulseBackground {
                        Surface(modifier = Modifier.padding(innerPadding)){
                            NewsPulseNavigationRoot(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
