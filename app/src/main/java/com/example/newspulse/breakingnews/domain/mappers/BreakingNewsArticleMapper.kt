package com.example.newspulse.breakingnews.domain.mappers

import com.example.newspulse.breakingnews.data.remote.ArticleResponse
import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList

fun ArticleResponse.toBreakingNewsArticle(): BreakingNewsArticle {
    return BreakingNewsArticle(
        sourceName = sourceResponse?.name ?: "",
        author = author,
        title = title,
        description = description,
        url = urlToArticle,
        urlToImage = urlToImage,
        publishedAt = publishedAt
    )
}

fun BreakingNewsResponse.toBreakingNewsList(): BreakingNewsList{
    return BreakingNewsList(
        articles = articleResponses?.map { it.toBreakingNewsArticle() } ?: emptyList()
    )
}