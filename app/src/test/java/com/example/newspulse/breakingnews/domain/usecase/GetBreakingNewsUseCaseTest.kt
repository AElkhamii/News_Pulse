package com.example.newspulse.breakingnews.domain.usecase

import com.example.newspulse.breakingnews.data.remote.ArticleResponse
import com.example.newspulse.breakingnews.data.remote.BreakingNewsResponse
import com.example.newspulse.breakingnews.data.remote.SourceResponse
import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList
import com.example.newspulse.breakingnews.domain.repo.BreakingNewsRepository
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBreakingNewsUseCaseTest {

    private lateinit  var getBreakingNewsUseCase: GetBreakingNewsUseCase
    private lateinit  var repo: BreakingNewsRepository

    @BeforeEach
    fun setUp() {
        // Instantiation
        repo = mockk()
        getBreakingNewsUseCase = GetBreakingNewsUseCase(repo)
    }

    // Given when then
    @Test
    fun `getBreakingNewsResponse on success should return BreakNewsList`() = runTest{
        //Arrange
        //->Parameters Initialization
        val country = "us"
        val category = "general"
        val pageSize = 20
        val page = 1
        //remote result
        val remoteArticleResponses = listOf(
            ArticleResponse(
                title = "Breaking: Major Event Happening Now!",
                description = "Breaking: Major Event Happening Now!",
                urlToImage = "https://www.example.com/image.jpg",
                sourceResponse = SourceResponse("MBC", "News Pulse"),
                publishedAt = "2025-02-18T12:00:00Z",
                author = "John Doe",
                urlToArticle = "https://www.example.com/article",
                content = "MBC",
            )
        )
        val stubRemoteBreakingNewsResponse = BreakingNewsResponse(
            articleResponses = remoteArticleResponses,
            totalResult = 1,
            status = "ok"
        )
        // Expected result
        val article = listOf(
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
        val stubBreakingNewsList = BreakingNewsList(article)
        val expectedResult = Result.Success(stubBreakingNewsList)

        //-> Mocking -- every for non coroutine function
        coEvery { repo.getBreakingNewsResponse(country,category,pageSize,page) } returns Result.Success(stubRemoteBreakingNewsResponse)

        //Act
        val actualResult = getBreakingNewsUseCase(country,category,pageSize,page)

        //Assert
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `getBreakingNewsResponse on error should return BreakNewsList`() = runTest{
        val country = "us"
        val category = "general"
        val pageSize = 20
        val page = 1

        coEvery { repo.getBreakingNewsResponse(country,category,pageSize,page) } returns Result.Error(DataError.Network.UNKNOWN)

        val actualResult = getBreakingNewsUseCase(country,category,pageSize,page)

        val expectedResult = Result.Error(DataError.Network.UNKNOWN)

        assertEquals(expectedResult,actualResult)
    }

//    @AfterEach
//    fun tearDown() {
//    }
}