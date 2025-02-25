package com.example.newspulse.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newspulse.core.data.database.dao.BreakingNewsListDao
import com.example.newspulse.core.data.database.entities.BreakingNewsArticleEntity

@Database(
    entities = [
        BreakingNewsArticleEntity::class
    ],
    version = 1
)
abstract class BreakingNewsDataBase: RoomDatabase() {
    abstract val breakingNewsListDao: BreakingNewsListDao
}