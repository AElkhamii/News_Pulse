package com.example.newspulse.breakingnews.domain.model


data class BreakingNewsArticle (
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)


