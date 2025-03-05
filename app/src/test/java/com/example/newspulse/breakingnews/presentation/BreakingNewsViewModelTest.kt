package com.example.newspulse.breakingnews.presentation

import com.example.newspulse.breakingnews.domain.model.BreakingNewsArticle
import com.example.newspulse.breakingnews.domain.model.BreakingNewsList
import com.example.newspulse.breakingnews.domain.usecase.CacheBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.ClearAllBreakingNewsUseCase
import com.example.newspulse.breakingnews.domain.usecase.GetBreakingNewsUseCase
import com.example.newspulse.breakingnews.presentation.breakingnews_list.BreakingNewsAction
import com.example.newspulse.breakingnews.presentation.breakingnews_list.BreakingNewsViewModel
import com.example.newspulse.core.domain.errorwrapper.DataError
import com.example.newspulse.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreakingNewsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var breakingNewsViewModel: BreakingNewsViewModel
    private lateinit var cacheBreakingNewsUseCase: CacheBreakingNewsUseCase
    private lateinit var clearAllBreakingNewsUseCase: ClearAllBreakingNewsUseCase
    private lateinit var getBreakingNewsUseCase: GetBreakingNewsUseCase
    private lateinit var networkHelper: NetworkHelper
    private lateinit var action : BreakingNewsAction


    private val stubEmptyBreakingNewsList = BreakingNewsList(emptyList())
    private val stubBreakingNewsList = BreakingNewsList(
        articles = listOf(
            BreakingNewsArticle(
                sourceName = "BBC News",
                author = "John Doe",
                title = "Breaking: Major Event Happening Now",
                description = "A major event has just occurred, details are still coming in.",
                url = "https://www.bbc.com/news/breaking-news",
                urlToImage = "https://www.bbc.com/news/image.jpg",
                publishedAt = "2025-02-24T12:00:00Z"
            ),
            BreakingNewsArticle(
                sourceName = "CNN",
                author = "Jane Smith",
                title = "Stock Market Sees Huge Changes",
                description = "The stock market has experienced significant fluctuations today.",
                url = "https://www.cnn.com/market-news",
                urlToImage = "https://www.cnn.com/market-news/image.jpg",
                publishedAt = "2025-02-24T10:30:00Z"
            )
        )
    )


    @BeforeEach
    fun setUp() {
        cacheBreakingNewsUseCase = mockk()
        clearAllBreakingNewsUseCase = mockk(relaxed = true)
        getBreakingNewsUseCase = mockk()
        networkHelper = mockk()
        breakingNewsViewModel = BreakingNewsViewModel(cacheBreakingNewsUseCase, getBreakingNewsUseCase, clearAllBreakingNewsUseCase, networkHelper)

        Dispatchers.setMain(testDispatcher) // Set the test dispatcher
    }
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after tests
        testDispatcher.cancel()
    }

    @Test
    fun `getFirstRequest clears cache and starts loading when online`() = runTest(testDispatcher){
        every { networkHelper.isInternetAvailable() } returns true
        coEvery { clearAllBreakingNewsUseCase() } returns Result.Success(Unit)
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Success(Unit)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubBreakingNewsList)

        action = BreakingNewsAction.OnLoadingBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        coVerify { clearAllBreakingNewsUseCase() }
        assertTrue(breakingNewsViewModel.state.isLoading)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(2, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `getFirstRequest clears cache and starts loading when online remote error`() = runTest(testDispatcher){
        every { networkHelper.isInternetAvailable() } returns true
        coEvery { clearAllBreakingNewsUseCase() } returns Result.Success(Unit)
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Error(DataError.Network.UNKNOWN)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubEmptyBreakingNewsList)

        action = BreakingNewsAction.OnLoadingBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        coVerify { clearAllBreakingNewsUseCase() }
        assertTrue(breakingNewsViewModel.state.isLoading)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubEmptyBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(1, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `getFirstRequest clears cache and starts loading when online room error`() = runTest(testDispatcher){
        every { networkHelper.isInternetAvailable() } returns true
        coEvery { clearAllBreakingNewsUseCase() } returns Result.Error(DataError.Local.UNAUTHORIZED_ACCESS)
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Success(Unit)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubEmptyBreakingNewsList)

        action = BreakingNewsAction.OnLoadingBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        coVerify { clearAllBreakingNewsUseCase() }
        assertTrue(breakingNewsViewModel.state.isLoading)
        assertTrue(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubEmptyBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(1, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `getFirstRequest clears cache and starts loading when offline`() = runTest(testDispatcher){
        every { networkHelper.isInternetAvailable() } returns false
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Error(DataError.Network.NO_INTERNET)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubEmptyBreakingNewsList)

        action = BreakingNewsAction.OnLoadingBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        // Ensure clearAllBreakingNewsUseCase() is NOT called
        coVerify(exactly = 0) { clearAllBreakingNewsUseCase() }
        assertTrue(breakingNewsViewModel.state.isLoading)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubEmptyBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(1, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `onAction OnLoadingMoreBreakingNews should load more articles when online`() = runTest(testDispatcher){
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Success(Unit)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubBreakingNewsList)
        every { networkHelper.isInternetAvailable() } returns true

        coEvery { clearAllBreakingNewsUseCase() } returns Result.Success(Unit)


        action = BreakingNewsAction.OnLoadingMoreBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        // Ensure clearAllBreakingNewsUseCase() is NOT called
        coVerify(exactly = 0) { clearAllBreakingNewsUseCase() }
        assertTrue(breakingNewsViewModel.state.isLoadingMore)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(2, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `onAction OnLoadingMoreBreakingNews should not load more articles when offline`() = runTest(testDispatcher){
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Success(Unit)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubBreakingNewsList)
        every { networkHelper.isInternetAvailable() } returns false

        coEvery { clearAllBreakingNewsUseCase() } returns Result.Success(Unit)


        action = BreakingNewsAction.OnLoadingMoreBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        // Ensure clearAllBreakingNewsUseCase() is NOT called
        coVerify(exactly = 0) { clearAllBreakingNewsUseCase() }
        coVerify(exactly = 0) { getBreakingNewsUseCase(any(), any()) }
        coVerify(exactly = 0) { cacheBreakingNewsUseCase(any(),any(),any(),any()) }
        assertFalse(breakingNewsViewModel.state.isLoadingMore)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(1, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `onAction OnRefreshingBreakingNews should reset page and load new data when online`() = runTest(testDispatcher) {
        every { networkHelper.isInternetAvailable() } returns true
        coEvery { clearAllBreakingNewsUseCase() } returns Result.Success(Unit)
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Success(Unit)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Success(stubBreakingNewsList)

        action = BreakingNewsAction.OnRefreshingBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        assertTrue(breakingNewsViewModel.state.isRefreshing)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(2, breakingNewsViewModel.currentPage)
    }

    @Test
    fun `onAction OnRefreshingBreakingNews should reset page and load new data when offline`() = runTest(testDispatcher) {
        every { networkHelper.isInternetAvailable() } returns false
        coEvery { clearAllBreakingNewsUseCase() } returns Result.Success(Unit)
        coEvery { cacheBreakingNewsUseCase(any(),any(),any(),any())} returns Result.Success(Unit)
        coEvery { getBreakingNewsUseCase(any(), any()) } returns Result.Error(DataError.Network.NO_INTERNET)

        action = BreakingNewsAction.OnRefreshingBreakingNews
        breakingNewsViewModel.onAction(action)
        advanceUntilIdle()

        coVerify(exactly = 0) { clearAllBreakingNewsUseCase() }
        assertTrue(breakingNewsViewModel.state.isRefreshing)
        assertFalse(breakingNewsViewModel.state.isLastPage)
        assertEquals(stubEmptyBreakingNewsList, breakingNewsViewModel.state.breakingNews)
        assertEquals(1, breakingNewsViewModel.currentPage)
    }


    @Test
    fun `onAction OnCategoryClick toggle isCategoryOn state`(){

        action = BreakingNewsAction.OnCategoryClick

        // actual action
        breakingNewsViewModel.onAction(action)

        val actualStateChange = breakingNewsViewModel.state.isCategoryOn

        assertTrue(actualStateChange)
    }

    @Test
    fun `onAction OnCategoryChange toggle category state`(){

        action = BreakingNewsAction.OnCategoryChange(breakingNewsViewModel.state.category)

        // actual action
        breakingNewsViewModel.onAction(action)

        val actualStateChange = breakingNewsViewModel.state.category

        assertEquals(actualStateChange,"general")
    }

    @Test
    fun `onAction OnCountryChange toggle country state`(){

        action = BreakingNewsAction.OnCountryChange(breakingNewsViewModel.state.country)

        // actual action
        breakingNewsViewModel.onAction(action)

        val actualStateChange = breakingNewsViewModel.state.country

        assertEquals(actualStateChange,"us")
    }

    @Test
    fun `onAction OnPageSizeChange toggle pageSize state`(){

        action = BreakingNewsAction.OnPageSizeChange(breakingNewsViewModel.state.pageSize)

        // actual action
        breakingNewsViewModel.onAction(action)

        val actualStateChange = breakingNewsViewModel.state.pageSize

        assertEquals(actualStateChange,"20")
    }
}