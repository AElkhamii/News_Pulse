package com.example.newspulse.core.data.database.mapper

import com.example.newspulse.breakingnews.data.remote.ArticleResponse
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity

fun ArticleResponse.toBreakingNewsEntity(): BreakingNewsArticleEntity{
    return BreakingNewsArticleEntity(
        sourceName = sourceResponse?.name ?: "",
        author = author,
        title = title,
        description = description,
        url = urlToArticle,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}