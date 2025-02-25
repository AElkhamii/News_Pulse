package com.example.newspulse.breakingnews.presentation.breakingnews_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.breakingnews.domain.usecase.CacheBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.GetBreakingNewsUseCase
import com.example.newspulse.core.domain.Constants
import com.example.newspulse.core.domain.util.Result
import com.example.newspulse.core.presentaion.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BreakingNewsViewModel(
    private val cacheBreakingNewsUseCase: CacheBreakingNewsUseCase,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
):ViewModel() {

    var currentPage = Constants.FIRST_BREAKING_NEWS_PAGE
        private set

    private var eventChannel = Channel<BreakingNewsEvent>()
    val event = eventChannel.receiveAsFlow()

    var state by mutableStateOf(BreakingNewsState())
        private set


    fun onAction(action: BreakingNewsAction){
        when(action){
            BreakingNewsAction.OnLoadingBreakingNews -> {
                getFirstRequest()
            }

            BreakingNewsAction.OnLoadingMoreBreakingNews -> {
                getMorePages()
            }

            BreakingNewsAction.OnRefreshingBreakingNews -> {
                getRefreshRequest()
            }

            BreakingNewsAction.OnItemClick -> {

            }

            BreakingNewsAction.OnCategoryClick -> {
                state = state.copy(isCategoryOn = !state.isCategoryOn)
            }

            is BreakingNewsAction.OnCategoryChange -> {
                state = state.copy(category = action.category)
            }
            is BreakingNewsAction.OnCountryChange -> {
                state = state.copy(country = action.country)
            }

            is BreakingNewsAction.OnPageSizeChange -> {
                state = state.copy(pageSize = action.pageSize)
            }
        }
    }

    private fun getFirstRequest(){
        if(state.isLoading) return // Prevent multiple requests

        currentPage = Constants.FIRST_BREAKING_NEWS_PAGE
        state = state.copy(isLoading = true, isLastPage = false)
        getBrakingNews()
    }

    private fun getMorePages(){
        if(state.isLoadingMore) return // Prevent multiple requests

        state = state.copy(isLoadingMore = true)
        getBrakingNews()
    }

    private fun getRefreshRequest(){
        if(state.isRefreshing) return // Prevent multiple requests

        currentPage = Constants.FIRST_BREAKING_NEWS_PAGE
        state = state.copy(isRefreshing = true, isLastPage = false)
        getBrakingNews()
    }

    private fun getBrakingNews(){
        viewModelScope.launch {

            val cashedResult = cacheBreakingNewsUseCase(
                country = state.country,
                category = state.category,
                pageSize = state.pageSize.toInt(),
                page = currentPage,
            )

            when(cashedResult){
                is Result.Error -> {
                    eventChannel.send(BreakingNewsEvent.Error(cashedResult.error.asUiText()))
                }
                is Result.Success -> {
                    val result = getBreakingNewsUseCase()
                    when(result){
                        is Result.Error -> {
                            eventChannel.send(BreakingNewsEvent.Error(result.error.asUiText()))
                        }
                        is Result.Success -> {
                            state = state.copy(isLastPage = result.data.articles.isEmpty())

                            state = if(state.isRefreshing || state.isLoading){
                                state.copy(
                                    breakingNews = result.data
                                )
                            } else{
                                state.copy(
                                    breakingNews = state.breakingNews.copy(
                                        articles = state.breakingNews.articles + result.data.articles
                                    )
                                )
                            }

                            if(!state.isLastPage){
                                currentPage++
                            }

                            eventChannel.send(BreakingNewsEvent.DataLoadedSuccessfully)
                        }
                    }
                }
            }



            state = state.copy(isLoading = false, isLoadingMore = false, isRefreshing = false)
        }
    }
}
