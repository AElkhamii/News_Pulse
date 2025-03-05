package com.example.newspulse.breakingnews.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class BreakingNewsResponse (
    val articles: List<ArticleResponse>? = null,
    val totalResult: Int? = null,
    val status: String? = null
)
