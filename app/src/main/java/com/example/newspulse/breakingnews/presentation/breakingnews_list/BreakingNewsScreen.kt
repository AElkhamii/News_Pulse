@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newspulse.breakingnews.presentation.breakingnews_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.newspulse.app.BottomNavigationBar
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseBackground
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme
import com.example.newspulse.core.domain.Constants
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseCircularProgressIndicator
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseNewsItem
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseSpinner
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseTopBar
import com.example.newspulse.core.presentaion.designsystem.theme.Dimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalPadding
import com.example.newspulse.core.presentaion.designsystem.theme.Padding
import com.example.newspulse.core.presentaion.ui.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun BreakingNewsScreenRoot(
    viewModel: BreakingNewsViewModel = koinViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(
        key1 = viewModel.state.country,
        key2 = viewModel.state.category,
        key3 = viewModel.state.pageSize,
    ) {
        viewModel.onAction(BreakingNewsAction.OnLoadingBreakingNews)
    }
    
    ObserveAsEvent(
        flow = viewModel.event,
    ) {event ->
        when(event){
            BreakingNewsEvent.DataLoadedSuccessfully -> {
                Toast.makeText(
                    context,
                    "Data Loaded Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            is BreakingNewsEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    BreakingNewsScreen(
        navController = navController,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun BreakingNewsScreen(
    navController: NavHostController,
    state: BreakingNewsState,
    onAction: (BreakingNewsAction) -> Unit
) {
    val dimensions = LocalDimensions.current
    val padding = LocalPadding.current

    Scaffold(
        topBar = {
            NewsPulseTopBar(
                title = "Breaking News",
                padding = padding,
                dimensions = dimensions,
                hasCategory = true,
                onCategoryClick = {onAction(BreakingNewsAction.OnCategoryClick)}
            )
        },
        bottomBar = { BottomNavigationBar(navController, dimensions) }
    ) {innerPadding->
        NewsPulseBackground {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ){
                val swipeRefreshState = rememberPullToRefreshState()

                if(state.isCategoryOn){
                    BreakingNewsFilterBar(
                        modifier = Modifier,
                        padding = padding,
                        dimensions = dimensions,
                        onAction = onAction,
                        state = state
                    )
                }
                if(state.isLoading && !state.isRefreshing){
                    NewsPulseCircularProgressIndicator(
                        modifier = Modifier,
                        dimensions = dimensions,
                        padding = padding
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = padding.tinyPadding, end = padding.tinyPadding)
                        .pullToRefresh(
                            isRefreshing = state.isRefreshing,
                            state = swipeRefreshState,
                            enabled = true,
                            threshold = PullToRefreshDefaults.PositionalThreshold,
                            onRefresh = {onAction(BreakingNewsAction.OnRefreshingBreakingNews)}
                        )
                ){
                    if (state.isRefreshing) {
                        item {
                            LoadMoreCircularProgressIndicator(padding = padding)
                        }
                    }
                    itemsIndexed(state.breakingNews.articles){index,article ->
                        NewsPulseNewsItem(
                            modifier = Modifier,
                            padding = padding,
                            dimensions = dimensions,
                            imageUrl = article.urlToImage?:"",
                            title = article.title,
                            description = article.description?:"",
                            source = article.sourceName,
                            publishedAt = article.publishedAt,
                            onFavouriteClick = {},
                            onItemClick = {},
                        )
                        // Detect when we reach the last item**
                        if (index == state.breakingNews.articles.lastIndex && !state.isLoadingMore && !state.isLastPage) {
                            onAction(BreakingNewsAction.OnLoadingMoreBreakingNews)
                        }
                    }
                    // Show Loading Indicator When Fetching More**
                    if (state.isLoadingMore) {
                        item {
                            LoadMoreCircularProgressIndicator(padding = padding)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BreakingNewsFilterBar(
    modifier: Modifier = Modifier,
    padding: Padding,
    dimensions: Dimensions,
    onAction: (BreakingNewsAction) -> Unit,
    state: BreakingNewsState
){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.filterBarHeight)
            .padding(start = padding.smallPadding, end = padding.smallPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){


        NewsPulseSpinner(
            modifier = Modifier
                .weight(1f)
                .padding(end = padding.tinyPadding),
            dimensions = dimensions,
            padding = padding,
            label = "Country",
            options = Constants.NEWS_COUNTRY,
            selectedOption = state.country,
            onOptionSelected = { selectedCountry-> onAction(BreakingNewsAction.OnCountryChange(selectedCountry)) }
        )

        NewsPulseSpinner(
            modifier = Modifier
                .weight(1f)
                .padding(end = padding.tinyPadding),
            dimensions = dimensions,
            padding = padding,
            label = "Category",
            options = Constants.NEWS_CATEGORIES,
            selectedOption = state.category,
            onOptionSelected = { selectedCategory-> onAction(BreakingNewsAction.OnCategoryChange(selectedCategory)) }
        )

        NewsPulseSpinner(
            modifier = Modifier
                .weight(1f)
                .padding(end = padding.tinyPadding),
            dimensions = dimensions,
            padding = padding,
            label = "Per Page",
            options = Constants.NEWS_PER_PAGE,
            selectedOption = state.pageSize,
            onOptionSelected = { selectedPageSize-> onAction(BreakingNewsAction.OnPageSizeChange(selectedPageSize)) }
        )
    }
}

@Composable
fun LoadMoreCircularProgressIndicator(
    modifier: Modifier = Modifier,
    padding: Padding
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.mediumPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun BreakingNewsScreenPreview() {
    NewsPulseTheme {
        NewsPulseBackground {
            val articlesList = listOf(
                BreakingNewsArticle(sourceName = "test1", author = "test1", title = "test1", description = "test1", url = "test1", urlToImage = "test1", publishedAt = "test1"),
                BreakingNewsArticle(sourceName = "test2", author = "test2", title = "test2", description = "test2", url = "test2", urlToImage = "test2", publishedAt = "test2"),
                BreakingNewsArticle(sourceName = "test3", author = "test3", title = "test3", description = "test3", url = "test3", urlToImage = "test3", publishedAt = "test3")
            )
            LazyColumn {
                items(articlesList) { article ->
                    NewsPulseNewsItem(
                        modifier = Modifier,
                        padding = LocalPadding.current,
                        dimensions = LocalDimensions.current,
                        imageUrl = article.urlToImage ?: "",
                        title = article.title,
                        description = article.description ?: "",
                        source = article.sourceName,
                        publishedAt = article.publishedAt,
                        onFavouriteClick = {},
                        onItemClick = {},
                    )
                }
            }
        }

    }
}