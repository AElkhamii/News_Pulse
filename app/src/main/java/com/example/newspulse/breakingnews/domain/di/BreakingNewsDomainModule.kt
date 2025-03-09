package com.example.newspulse.breakingnews.domain.di

import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.breakingnews.domain.usecase.CacheBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.ClearAllBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.GetBreakingNewsUseCase
import org.koin.dsl.module

val breakingNewsDomainModule = module {
    single { CacheBreakingNewsUseCase(get<BreakingNewsRepository>()) }
    single { GetBreakingNewsUseCase(get<BreakingNewsRepository>()) }
    single { ClearAllBreakingNewsUseCase(get<BreakingNewsRepository>()) }
}