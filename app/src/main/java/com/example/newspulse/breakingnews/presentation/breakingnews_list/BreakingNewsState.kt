package com.example.newspulse.breakingnews.presentation.breakingnews_list

import com.example.newspulse.breakingnews.domain.model.BreakingNewsList

data class BreakingNewsState(
    val country: String = "us",
    val category: String = "general",
    val pageSize: String = "20",
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isLastPage: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFavourite: Boolean = false,
    val isCategoryOn: Boolean = false,
    val breakingNews: BreakingNewsList = BreakingNewsList(emptyList())
)
