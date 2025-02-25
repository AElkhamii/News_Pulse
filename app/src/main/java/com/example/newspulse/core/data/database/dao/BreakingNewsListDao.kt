package com.example.newspulse.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity

@Dao
interface BreakingNewsListDao {
    @Query("SELECT * FROM breaking_news")
    suspend fun getBreakingNewsEntities(): List<BreakingNewsArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreakingNewsEntities(articles: List<BreakingNewsArticleEntity>)

    @Query("DELETE FROM breaking_news")
    suspend fun clearAllBreakingNewsEntity()
}