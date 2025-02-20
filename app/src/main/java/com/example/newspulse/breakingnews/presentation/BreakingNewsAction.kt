package com.example.newspulse.breakingnews.presentation

sealed interface BreakingNewsAction {
    data object OnItemClick: BreakingNewsAction
}