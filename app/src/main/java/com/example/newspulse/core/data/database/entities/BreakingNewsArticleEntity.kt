package com.example.newspulse.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breaking_news")
data class BreakingNewsArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Auto-generated primary key
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)
