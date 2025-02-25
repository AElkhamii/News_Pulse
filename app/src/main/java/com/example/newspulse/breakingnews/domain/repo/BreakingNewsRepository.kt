package com.example.newspulse.breakingnews.domain.repo

import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result

interface BreakingNewsRepository {
    suspend fun getBreakingNewsResponse(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ): Result<BreakingNewsResponse, DataError.Network>
}