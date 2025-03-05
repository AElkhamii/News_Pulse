package com.example.newspulse.breakingnews.data

import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.example.newspulse.BuildConfig
import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.domain.mappers.toBreakingNewsArticleEntity
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.data.database.dao.BreakingNewsListDao
import com.example.newspulse.core.data.database.mapper.toBreakingNewsArticle
import com.example.newspulse.core.data.network.get
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.EmptyResult
import com.example.newspulse.core.domain.util.Result
import io.ktor.client.HttpClient

class BreakingNewsRepositoryImp(
    private val httpClient: HttpClient,
    private val breakingNewsListDao: BreakingNewsListDao
): BreakingNewsRepository {

    override suspend fun getRemoteBreakingNewsResponse(country: String, category: String, pageSize: Int, page: Int): Result<BreakingNewsResponse, DataError.Network> {
        return httpClient.get<BreakingNewsResponse>(
            route = "/v2/top-headlines",
            queryParameters = mapOf("country" to country, "category" to category, "pageSize" to pageSize, "page" to page, "apiKey" to BuildConfig.API_KEY)
        )
    }

    override suspend fun cacheBreakingNewsList(country: String, category: String, pageSize: Int, page: Int): EmptyResult<DataError> {
        val remoteResult = getRemoteBreakingNewsResponse(country, category, pageSize, page)
        return when (remoteResult) {
            is Result.Error -> Result.Error(remoteResult.error) // Handles network errors
            is Result.Success -> {
                try {
                    breakingNewsListDao.insertBreakingNewsEntities(remoteResult.data.articles?.map { it.toBreakingNewsArticleEntity() } ?: emptyList())
                    Result.Success(Unit)
                } catch (e: SQLiteFullException) {
                    Result.Error(DataError.Local.DISK_IS_FULL)
                }
                catch (e: SQLiteException) {
                    Result.Error(DataError.Local.DATA_CORRUPTED)
                } catch (e: SecurityException) {
                    Result.Error(DataError.Local.UNAUTHORIZED_ACCESS)
                } catch (e: Exception) {
                    Result.Error(DataError.Local.DATABASE_FAILURE)
                }
            }
        }
    }

    override suspend fun getCachedBreakingNewsList(pageSize: Int, offset: Int): Result<List<BreakingNewsArticle>,DataError.Local> {
        return try {
            val cachedBreakingNews = breakingNewsListDao.getBreakingNewsEntities(pageSize, offset).map { it.toBreakingNewsArticle() }
            if (cachedBreakingNews.isNotEmpty()) {
                Result.Success(cachedBreakingNews)
            } else {
                Result.Error(DataError.Local.NO_DATA) // New error type for empty cache
            }
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DATA_CORRUPTED)
        } catch (e: SecurityException) {
            Result.Error(DataError.Local.UNAUTHORIZED_ACCESS)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DATABASE_FAILURE)
        }
    }

    override suspend fun clearAllBreakingNewsEntity(): EmptyResult<DataError> {
        return try {
            breakingNewsListDao.clearAllBreakingNewsEntity()
            Result.Success(Unit)
        } catch (e: SecurityException) {
            Result.Error(DataError.Local.UNAUTHORIZED_ACCESS)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DATABASE_FAILURE)
        }
    }

}