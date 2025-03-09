package com.example.newspulse.breakingnews.data.di

import com.example.newspulse.breakingnews.data.BreakingNewsRepositoryImp
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val breakingNewsDataModule = module{
    singleOf(::BreakingNewsRepositoryImp).bind<BreakingNewsRepository>()
}