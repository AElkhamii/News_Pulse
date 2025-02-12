package com.example.newspulse.app

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.core.presentaion.designsystem.theme.Navigation_News
import com.example.newspulse.core.presentaion.designsystem.theme.Navigation_Save
import com.example.newspulse.core.presentaion.designsystem.theme.Navigation_Search
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme


@Composable
fun NewsPulseNavigationRoot(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        bottomNavGraph(navController)
    }
}

private fun NavGraphBuilder.bottomNavGraph(navController: NavHostController){
    navigation(
        route = "start",
        startDestination = "news"
    ){
        composable(route = "news"){ Text(text = "news")}
        composable(route = "web"){ Text(text = "web")}

        composable(route = "save"){Text(text = "save")}
        composable(route = "search"){Text(text = "search")}
    }
}


@Composable
fun BottomNavigationBar (navController: NavHostController){
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            selected = currentRoute == "news",
            icon = { Icon(imageVector = Navigation_News, contentDescription = "news") },
            onClick = {
                navController.navigate(route = "news"){
                    launchSingleTop = true
                    popUpTo("news"){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            selected = currentRoute == "save",
            icon = { Icon(imageVector = Navigation_Save, contentDescription = "save") },
            onClick = {
                navController.navigate(route = "save"){
                    launchSingleTop = true
                    popUpTo("news"){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            selected = currentRoute == "search",
            icon = { Icon(imageVector = Navigation_Search, contentDescription = "search") },
            onClick = {
                navController.navigate(route = "search"){
                    popUpTo("news"){
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
        BottomNavigationBar(rememberNavController())
    }
}
