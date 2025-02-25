package com.example.newspulse.breakingnews.domain.di

import com.example.newspulse.breakingnews.domain.usecase.GetBreakingNewsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val breakingNewsDomainModule = module {
    single { GetBreakingNewsUseCase(get()) }
}