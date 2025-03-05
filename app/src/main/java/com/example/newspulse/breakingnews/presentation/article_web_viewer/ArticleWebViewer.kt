package com.example.newspulse.breakingnews.presentation.article_web_viewer

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.newspulse.app.BottomNavigationBar
import com.example.newspulse.breakingnews.presentation.breakingnews_list.BreakingNewsAction
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions

@Composable
fun WebViewWithBackHandler(
    url: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    dimensions: Dimensions,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    var canGoBack by remember { mutableStateOf(false) }
    var progress by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, dimensions) },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Favorite, contentDescription = "Refresh")
            }
        }
    ) {innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (progress in 1..99) { // Show only when loading
                LinearProgressIndicator(
                    progress = { progress / 100f },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { context ->
                    webView.apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.allowContentAccess = true

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                return false // Load URLs inside the WebView
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                canGoBack = view?.canGoBack() == true
                            }
                        }

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                progress = newProgress // Update progress bar
                            }
                        }
                        loadUrl(url)
                    }
                },
                /*
                * If canGoBack() is true, it updates canGoBack = true and returns true.
                * If canGoBack() is false, it updates canGoBack = false and returns false.
                * */
                update = { webViewBackState ->
                    webViewBackState.canGoBack().also { canGoBack = it }
                }
            )
        }

        BackHandler(canGoBack) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                onBackPressed()
            }
        }
    }
}
