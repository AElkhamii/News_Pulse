package com.example.newspulse.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity

@Dao
interface BreakingNewsListDao {
    @Query("SELECT * FROM breaking_news")
    suspend fun getBreakingNewsEntities(): List<BreakingNewsArticleEntity>

    @Upsert
    suspend fun upsertBreakingNewsEntities(articles: List<BreakingNewsArticleEntity>)
}