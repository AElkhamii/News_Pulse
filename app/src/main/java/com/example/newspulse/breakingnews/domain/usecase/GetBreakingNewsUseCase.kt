package com.example.newspulse.breakingnews.domain.usecase

import com.example.newspulse.breakingnews.domain.model.BreakingNewsList
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result

class GetBreakingNewsUseCase (
    private val repo: BreakingNewsRepository,
) {
    suspend operator fun invoke(pageSize: Int, page: Int): Result<BreakingNewsList,DataError> {
        val offset = (page - 1) * pageSize
        val result = repo.getCachedBreakingNewsList(pageSize, offset)
        return when(result){
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(BreakingNewsList(result.data))
        }
    }
}