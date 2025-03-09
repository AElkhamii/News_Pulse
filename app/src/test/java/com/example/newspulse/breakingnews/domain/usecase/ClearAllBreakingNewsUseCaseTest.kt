package com.example.newspulse.breakingnews.domain.usecase

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

class ClearAllBreakingNewsUseCaseTest {

    private lateinit var clearAllBreakingNewsUseCase: ClearAllBreakingNewsUseCase
    private lateinit var repo: BreakingNewsRepository


    @BeforeEach
    fun setUp() {
        repo = mockk()
        clearAllBreakingNewsUseCase = ClearAllBreakingNewsUseCase(repo)
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `onClear all data base successfully`() = runTest {
        coEvery { repo.clearAllBreakingNewsEntity() } returns Result.Success(Unit)

        val actualResult = clearAllBreakingNewsUseCase()
        val expectedResult = Result.Success(Unit)

        assertEquals(expectedResult,actualResult)
    }

    @Test
    fun `onClear all data base Error`() = runTest {
        coEvery { repo.clearAllBreakingNewsEntity() } returns Result.Error(DataError.Network.UNKNOWN)

        val actualResult = clearAllBreakingNewsUseCase()
        val expectedResult = Result.Error(DataError.Network.UNKNOWN)

        assertEquals(expectedResult,actualResult)
    }
}