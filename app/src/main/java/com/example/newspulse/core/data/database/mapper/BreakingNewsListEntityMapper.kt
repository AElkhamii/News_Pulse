package com.example.newspulse.core.data.database.mapper

import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity

fun BreakingNewsArticleEntity.toBreakingNewsArticle(): BreakingNewsArticle {
    return BreakingNewsArticle(
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}