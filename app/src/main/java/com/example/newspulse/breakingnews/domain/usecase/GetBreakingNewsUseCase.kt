package com.example.newspulse.breakingnews.domain.usecase

import com.example.newspulse.breakingnews.domain.mappers.toBreakingNewsList
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result

class GetBreakingNewsUseCase(
    private val repo: BreakingNewsRepository
) {
    suspend operator fun invoke(country: String, category: String, pageSize: Int, page: Int): Result<BreakingNewsList,DataError.Network>{
        val result =  repo.getBreakingNewsResponse(country, category, pageSize, page)
        return when(result){
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(result.data.toBreakingNewsList())
        }
    }
}