package com.example.jetbooks.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetbooks.data.BookRepository
import com.example.jetbooks.model.Book
import com.example.jetbooks.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<List<Book>>>
        get() = _uiState

    init {
        getAllBooks()
    }

    private fun getAllBooks(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBooks())
        }
    }
}