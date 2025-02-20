package com.example.newspulse.breakingnews.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.breakingnews.domain.usecase.GetBreakingNewsUseCase
import com.example.newspulse.core.domain.util.Result
import kotlinx.coroutines.launch

class BreakingNewsViewModel(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase
):ViewModel() {



    var state by mutableStateOf(BreakingNewsState())
        private set


    fun onAction(action:BreakingNewsAction){
        when(action){
            BreakingNewsAction.OnItemClick -> TODO()
        }
    }

    fun getRemoteBrakingNews(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = getBreakingNewsUseCase(
                country = state.country,
                category = state.category,
                pageSize = 40,
                page = 1,
            )
            state = state.copy(isLoading = false)

            when(result){
                is Result.Error -> {}
                is Result.Success -> {
                    state = state.copy(breakingNews = result.data)
                }
            }
        }
    }
}
