package com.example.newspulse.breakingnews.presentation.breakingnews_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList
import com.example.newspulse.breakingnews.domain.usecase.CacheBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.ClearAllBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.GetBreakingNewsUseCase
import com.example.newspulse.breakingnews.presentation.NetworkHelper
import com.example.newspulse.core.domain.Constants
import com.example.newspulse.core.domain.util.Result
import com.example.newspulse.core.presentaion.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BreakingNewsViewModel(
    private val cacheBreakingNewsUseCase: CacheBreakingNewsUseCase,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val clearAllBreakingNewsUseCase: ClearAllBreakingNewsUseCase,
    private val networkHelper: NetworkHelper
):ViewModel() {

    var currentPage = Constants.FIRST_BREAKING_NEWS_PAGE

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

        if(networkHelper.isInternetAvailable()){
            viewModelScope.launch {
                clearAllBreakingNewsUseCase()
            }
            state = state.copy(breakingNews = BreakingNewsList(emptyList()))
        }

        currentPage = Constants.FIRST_BREAKING_NEWS_PAGE
        state = state.copy(isLoading = true, isLastPage = false)
        getBrakingNews()
    }

    private fun getMorePages(){
        if(state.isLoadingMore) return // Prevent multiple requests

        if(networkHelper.isInternetAvailable()){
            state = state.copy(isLoadingMore = true)
            getBrakingNews()
        }
    }

    private fun getRefreshRequest(){
        if(state.isRefreshing) return // Prevent multiple requests

        if(networkHelper.isInternetAvailable()){
            viewModelScope.launch {
                clearAllBreakingNewsUseCase()
            }
            state = state.copy(breakingNews = BreakingNewsList(emptyList()))
        }

        currentPage = Constants.FIRST_BREAKING_NEWS_PAGE
        state = state.copy(isRefreshing = true, isLastPage = false)
        getBrakingNews()
    }

    private fun getBrakingNews(){
        viewModelScope.launch {
            val cachingResult = cacheBreakingNewsUseCase(
                country = state.country,
                category = state.category,
                pageSize = state.pageSize.toInt(),
                page = currentPage,
            )

            val cashedResult = getBreakingNewsUseCase(state.pageSize.toInt(),currentPage)

            when(cachingResult){
                is Result.Error -> {
                    eventChannel.send(BreakingNewsEvent.Error(cachingResult.error.asUiText()))
                    when(cashedResult){
                        is Result.Error -> {
                            eventChannel.send(BreakingNewsEvent.Error(cashedResult.error.asUiText()))
                        }
                        is Result.Success -> {
                            state = state.copy(isLastPage = cashedResult.data.articles.isEmpty())

                            state = if(state.isRefreshing || state.isLoading){
                                state.copy(
                                    breakingNews = cashedResult.data
                                )
                            } else{
                                state.copy(
                                    breakingNews = state.breakingNews.copy(
                                        articles = state.breakingNews.articles + cashedResult.data.articles
                                    )
                                )
                            }
                        }
                    }
                }
                is Result.Success -> {
                    when(cashedResult){
                        is Result.Error -> {
                            eventChannel.send(BreakingNewsEvent.Error(cashedResult.error.asUiText()))
                        }
                        is Result.Success -> {
                            state = state.copy(isLastPage = cashedResult.data.articles.isEmpty())

                            state = if(state.isRefreshing || state.isLoading){
                                state.copy(
                                    breakingNews = cashedResult.data
                                )
                            } else{
                                state.copy(
                                    breakingNews = state.breakingNews.copy(
                                        articles = state.breakingNews.articles + cashedResult.data.articles
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
