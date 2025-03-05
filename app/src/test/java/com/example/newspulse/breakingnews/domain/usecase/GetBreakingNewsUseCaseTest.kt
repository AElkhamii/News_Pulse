package com.example.newspulse.breakingnews.domain.usecase

import com.example.newspulse.breakingnews.data.remote.ArticleResponse
import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.data.remote.Source
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBreakingNewsUseCaseTest {

    private lateinit var getBreakingNewsUseCase: GetBreakingNewsUseCase
    private lateinit var repo: BreakingNewsRepository


    @BeforeEach
    fun setUp() {
        repo = mockk()
        getBreakingNewsUseCase = GetBreakingNewsUseCase(repo)

    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `getBreakingNews on success should return result with data`() = runTest {
        //  remote result
        val remoteArticleResponses = listOf(
            ArticleResponse(
                title = "Breaking: Major Event Happening Now!",
                description = "Breaking: Major Event Happening Now!",
                urlToImage = "https://www.example.com/image.jpg",
                source = Source("MBC", "News Pulse"),
                publishedAt = "2025-02-18T12:00:00Z",
                author = "John Doe",
                urlToArticle = "https://www.example.com/article",
                content = "MBC",
            )
        )
        val stubRemoteBreakingNewsResponse = BreakingNewsResponse(
            articles = remoteArticleResponses,
            totalResult = 1,
            status = "ok"
        )
        // Expected result
        val articles = listOf(
            BreakingNewsArticle(
                sourceName = "News Pulse",
                author = "John Doe",
                title = "Breaking: Major Event Happening Now!",
                description = "Breaking: Major Event Happening Now!",
                url = "https://www.example.com/article",
                urlToImage = "https://www.example.com/image.jpg",
                publishedAt = "2025-02-18T12:00:00Z"
            )
        )

        coEvery { repo.getCachedBreakingNewsList(any(), any()) } returns Result.Success(articles)
        val actualResult = getBreakingNewsUseCase(20,1)
        val expectedResult = Result.Success(BreakingNewsList(articles))

        assertEquals(expectedResult,actualResult)
    }

    @Test
    fun `getBreakingNews on success should return result with Error`() = runTest {

        coEvery { repo.getCachedBreakingNewsList(any(), any()) } returns Result.Error(DataError.Local.DISK_IS_FULL)
        val actualResult = getBreakingNewsUseCase(20,1)
        val expectedResult = Result.Error(DataError.Local.DISK_IS_FULL)

        assertEquals(expectedResult,actualResult)

    }
}