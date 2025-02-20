package com.example.newspulse.breakingnews.presentation.di

import com.example.newspulse.breakingnews.presentation.BreakingNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val breakingNewsPresentationModule = module {
    viewModelOf(::BreakingNewsViewModel)
}