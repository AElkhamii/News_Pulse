package com.example.newspulse.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseBackground
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseTopBar
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalPadding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsPulseTheme {
                val padding = LocalPadding.current
                val dimensions = LocalDimensions.current
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                Scaffold(
                    topBar = {
                        when (currentRoute) {
                            "news" -> NewsPulseTopBar(
                                title = "Breaking News",
                                padding = padding,
                                dimensions = dimensions,
                                hasCategory = true,
                                onCategoryClick = {}
                            )

                            "save" -> NewsPulseTopBar(
                                title = "Saved Articles",
                                padding = padding,
                                dimensions = dimensions,
                                hasBackArrow = true,
                                onBackClick = { navController.popBackStack() }
                            )

                            "search" -> NewsPulseTopBar(
                                title = "Search",
                                padding = padding,
                                dimensions = dimensions,
                                hasBackArrow = true,
                                onBackClick = { navController.popBackStack() }
                            )

                            else -> {} // No top bar for other screens
                        }
                    },
                    bottomBar = {BottomNavigationBar(navController)}
                ) { innerPadding ->
                    NewsPulseBackground(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                            NewsPulseNavigationRoot(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
