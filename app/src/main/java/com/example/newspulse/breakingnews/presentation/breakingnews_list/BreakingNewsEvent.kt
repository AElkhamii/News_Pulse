package com.example.newspulse.breakingnews.presentation.breakingnews_list

import com.example.newspulse.core.presentaion.ui.UiText

sealed interface BreakingNewsEvent {
    data object DataLoadedSuccessfully: BreakingNewsEvent
    data class Error(val error: UiText): BreakingNewsEvent
}