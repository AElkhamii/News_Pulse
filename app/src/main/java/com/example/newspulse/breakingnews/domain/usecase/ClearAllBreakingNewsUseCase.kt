package com.example.newspulse.breakingnews.domain.usecase

import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.EmptyResult

class ClearAllBreakingNewsUseCase(
    private val repo: BreakingNewsRepository
) {
    suspend operator fun invoke(): EmptyResult<DataError>{
        return repo.clearAllBreakingNewsEntity()
    }
}