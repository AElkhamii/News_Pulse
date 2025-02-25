package com.example.newspulse.breakingnews.domain.repo

import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.EmptyResult
import com.example.newspulse.core.domain.util.Result

interface BreakingNewsRepository {
    suspend fun getRemoteBreakingNewsResponse(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ): Result<BreakingNewsResponse, DataError.Network>

    suspend fun cacheBreakingNewsList(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ): EmptyResult<DataError>

    suspend fun getCachedBreakingNewsList(): Result<List<BreakingNewsArticle>,DataError.Local>


    suspend fun clearAllBreakingNewsEntity(): EmptyResult<DataError>

}