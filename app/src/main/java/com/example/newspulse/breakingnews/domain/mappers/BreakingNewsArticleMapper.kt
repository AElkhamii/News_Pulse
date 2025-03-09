package com.example.newspulse.breakingnews.domain.mappers

import com.example.newspulse.breakingnews.data.remote.ArticleResponse
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity


fun ArticleResponse.toBreakingNewsArticleEntity(): BreakingNewsArticleEntity{
    return BreakingNewsArticleEntity(
        sourceName = source?.name ?: "",
        author = author,
        title = title,
        description = description,
        url = urlToArticle,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}