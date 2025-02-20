package com.example.newspulse.breakingnews.domain.mappers

import com.example.newspulse.breakingnews.data.remote.Article
import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList

fun Article.toBreakingNewsArticle(): BreakingNewsArticle {
    return BreakingNewsArticle(
        sourceName = source?.name ?: "",
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
        articles = articles?.map { it.toBreakingNewsArticle() } ?: emptyList()
    )
}