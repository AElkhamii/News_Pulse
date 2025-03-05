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

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CacheBreakingNewsUseCaseTest {

    private lateinit  var cacheBreakingNewsUseCase: CacheBreakingNewsUseCase
    private lateinit  var repo: BreakingNewsRepository

    @BeforeEach
    fun setUp() {
        // Instantiation
        repo = mockk()
        cacheBreakingNewsUseCase = CacheBreakingNewsUseCase(repo)
    }

    // Given when then
    @Test
    fun `cacheBreakingNewsResponse on success should return EmptyList`() = runTest{
        //Arrange
        //->Parameters Initialization
        val country = "us"
        val category = "general"
        val pageSize = 20
        val page = 1

        val expectedResult = Result.Success(Unit)

        //-> Mocking -- every for non coroutine function
        coEvery { repo.cacheBreakingNewsList(country,category,pageSize,page) } returns Result.Success(Unit)

        //Act
        val actualResult = cacheBreakingNewsUseCase(country,category,pageSize,page)

        //Assert
        assertEquals(expectedResult, actualResult)
    }


    @Test
    fun `cacheBreakingNewsResponse on success should return Error`() = runTest{
        val country = "us"
        val category = "general"
        val pageSize = 20
        val page = 1

        coEvery { repo.cacheBreakingNewsList(country,category,pageSize,page) } returns Result.Error(DataError.Network.UNKNOWN)

        val actualResult = cacheBreakingNewsUseCase(country,category,pageSize,page)

        val expectedResult = Result.Error(DataError.Network.UNKNOWN)

        assertEquals(expectedResult,actualResult)
    }

//    @AfterEach
//    fun tearDown() {
//    }
}