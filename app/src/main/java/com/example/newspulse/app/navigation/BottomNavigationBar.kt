package com.example.newspulse.app.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.app.navigation.MainRoutes.BREAKING_NEWS
import com.example.newspulse.app.navigation.MainRoutes.SAVED_ARTICLES
import com.example.newspulse.app.navigation.MainRoutes.SEARCH_ARTICLES
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.Navigation_News
import com.example.newspulse.core.presentaion.designsystem.theme.Navigation_Save
import com.example.newspulse.core.presentaion.designsystem.theme.Navigation_Search
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme

@Composable
fun BottomNavigationBar (navController: NavHostController, dimensions: Dimensions){
    NavigationBar(modifier = Modifier.height(dimensions.bottomBarHeight)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            selected = currentRoute == BREAKING_NEWS,
            icon = { Icon(imageVector = Navigation_News, contentDescription = BREAKING_NEWS) },
            onClick = {
                navController.navigate(route = BREAKING_NEWS){
                    launchSingleTop = true
                    popUpTo(BREAKING_NEWS){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            selected = currentRoute == SAVED_ARTICLES,
            icon = { Icon(imageVector = Navigation_Save, contentDescription = SAVED_ARTICLES) },
            onClick = {
                navController.navigate(route = SAVED_ARTICLES){
                    launchSingleTop = true
                    popUpTo(BREAKING_NEWS){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            selected = currentRoute == SEARCH_ARTICLES,
            icon = { Icon(imageVector = Navigation_Search, contentDescription = SEARCH_ARTICLES) },
            onClick = {
                navController.navigate(route = SEARCH_ARTICLES){
                    popUpTo(BREAKING_NEWS){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Preview
@Composable
private fun BottomNavigationBarPreview () {
    NewsPulseTheme {
        BottomNavigationBar(rememberNavController(), LocalDimensions.current)
    }
}
