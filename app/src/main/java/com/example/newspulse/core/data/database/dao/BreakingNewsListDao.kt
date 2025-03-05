package com.example.newspulse.core.data.database.dao

import androidx.room.Dao

import androidx.room.Query
import androidx.room.Upsert
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity

@Dao
interface BreakingNewsListDao {
    @Query("SELECT * FROM breaking_news ORDER BY publishedAt DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getBreakingNewsEntities(pageSize: Int, offset: Int): List<BreakingNewsArticleEntity>

    @Upsert
    suspend fun insertBreakingNewsEntities(articles: List<BreakingNewsArticleEntity>)

    @Query("DELETE FROM breaking_news")
    suspend fun clearAllBreakingNewsEntity()
}