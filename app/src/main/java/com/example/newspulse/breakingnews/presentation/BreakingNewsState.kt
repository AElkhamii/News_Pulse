package com.example.newspulse.breakingnews.presentation

import com.example.newspulse.breakingnews.domain.model.BreakingNewsList

data class BreakingNewsState(
    val country: String = "us",
    val category: String = "",
    val isLoading: Boolean = false,
    val isFavourite: Boolean = false,
    val isCategoryOn: Boolean = false,
    val breakingNews: BreakingNewsList = BreakingNewsList(emptyList())
)
