package com.example.newspulse.breakingnews.presentation.di

import com.example.newspulse.breakingnews.presentation.breakingnews_list.BreakingNewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val breakingNewsPresentationModule = module {
    viewModel { BreakingNewsViewModel(get()) }
}