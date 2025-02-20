package com.example.newspulse.breakingnews.data

import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.data.network.get
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result
import io.ktor.client.HttpClient

class BreakingNewsRepositoryImp(private val httpClient: HttpClient): BreakingNewsRepository {
    override suspend fun getBreakingNewsResponse(country: String, category: String, pageSize: Int, page: Int, apiKey: String): Result<BreakingNewsResponse, DataError.Network> {
        return httpClient.get<BreakingNewsResponse>(
            route = "/v2/top-headlines",
            queryParameters = mapOf("country" to country, "category" to category, "pageSize" to pageSize, "page" to page, "apiKey" to apiKey)
        )
    }
}