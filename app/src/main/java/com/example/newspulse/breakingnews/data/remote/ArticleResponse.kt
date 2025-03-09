package com.example.newspulse.breakingnews.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    val author: String?,
    val title: String,
    val description: String?,
    @SerialName("url")
    val urlToArticle: String,
    @SerialName("urlToImage")
    val urlToImage: String?,
    val source: Source?,
    val publishedAt: String,
    val content: String?
)