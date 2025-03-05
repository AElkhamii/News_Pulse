package com.example.newspulse.breakingnews.domain.usecase

import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.EmptyResult

class CacheBreakingNewsUseCase(
    private val repo: BreakingNewsRepository,
) {
    suspend operator fun invoke(country: String, category: String, pageSize: Int, page: Int): EmptyResult<DataError>{
        return repo.cacheBreakingNewsList(country, category, pageSize, page)
    }
}