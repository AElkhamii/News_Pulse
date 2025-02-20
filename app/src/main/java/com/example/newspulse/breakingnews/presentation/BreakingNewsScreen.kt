import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier import androidx.compose.ui.tooling.preview.Preview
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseBackground
import com.example.newspulse.core.presentaion.designsystem.theme.NewsPulseTheme
import com.example.newspulse.breakingnews.presentation.BreakingNewsAction
import com.example.newspulse.breakingnews.presentation.BreakingNewsState
import com.example.newspulse.breakingnews.presentation.BreakingNewsViewModel
import com.example.newspulse.core.presentaion.designsystem.components.NewsPulseNewsItem
import com.example.newspulse.core.presentaion.designsystem.theme.LocalDimensions
import com.example.newspulse.core.presentaion.designsystem.theme.LocalPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun BreakingNewsScreenRoot(
    viewModel: BreakingNewsViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        if(viewModel.state.breakingNews.articles.isEmpty()){
            viewModel.getRemoteBrakingNews()
        }
    }

    BreakingNewsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun BreakingNewsScreen(
    state: BreakingNewsState,
    onAction: (BreakingNewsAction) -> Unit
) {
    val dimensions = LocalDimensions.current
    val padding = LocalPadding.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.isLoading){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensions.progressBarSize),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = padding.tinyPadding
                )
                Spacer(Modifier.height(dimensions.largeSpace))
                Text(
                    text = "Loading...",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = padding.tinyPadding, end = padding.tinyPadding)
        ){
            items(state.breakingNews.articles){article ->
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
            }
        }
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