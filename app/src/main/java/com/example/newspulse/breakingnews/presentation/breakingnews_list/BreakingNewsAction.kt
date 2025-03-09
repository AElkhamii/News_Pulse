package com.example.newspulse.breakingnews.presentation.breakingnews_list

sealed interface BreakingNewsAction {
    data object OnLoadingBreakingNews: BreakingNewsAction
    data object OnLoadingMoreBreakingNews: BreakingNewsAction
    data object OnRefreshingBreakingNews: BreakingNewsAction
    data object OnItemClick: BreakingNewsAction
    data object OnCategoryClick: BreakingNewsAction
    data class OnCountryChange(val country: String): BreakingNewsAction
    data class OnCategoryChange(val category: String): BreakingNewsAction
    data class OnPageSizeChange(val pageSize: String): BreakingNewsAction
}