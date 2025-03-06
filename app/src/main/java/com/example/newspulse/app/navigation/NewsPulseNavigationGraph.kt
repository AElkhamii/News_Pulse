package com.example.newspulse.app.navigation

import android.net.Uri
import com.example.newspulse.breakingnews.presentation.breakingnews_list.BreakingNewsScreenRoot
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.newspulse.app.navigation.MainRoutes.BREAKING_NEWS
import com.example.newspulse.app.navigation.MainRoutes.SAVED_ARTICLES
import com.example.newspulse.app.navigation.MainRoutes.SEARCH_ARTICLES
import com.example.newspulse.breakingnews.presentation.article_web_viewer.WebViewWithBackHandler
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.app.navigation.MainRoutes.START
import com.example.newspulse.app.navigation.MainRoutes.WEB_VIEWER

@Composable
fun NewsPulseNavigationRoot(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = START
    ) {
        bottomNavGraph(navController)
    }
}

    private fun NavGraphBuilder.bottomNavGraph(navController: NavHostController){
        navigation(
            route = START,
            startDestination = BREAKING_NEWS
        ){
            composable(route = BREAKING_NEWS){ BreakingNewsScreenRoot(navController = navController) }
            composable(route = "$WEB_VIEWER/{url}") { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url")?.let { Uri.decode(it) } ?: ""
                WebViewWithBackHandler(
                    url = url,
                    modifier = Modifier,
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    navController = navController,
                    dimensions = LocalDimensions.current,
                )
            }

        composable(route = SAVED_ARTICLES){Text(text = "save")}
        composable(route = SEARCH_ARTICLES){Text(text = "search")}
    }
}

