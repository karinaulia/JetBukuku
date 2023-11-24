package com.bangkit.jetbukuku.ui.screen.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jetbukuku.data.BookRepository
import com.bangkit.jetbukuku.model.Book
import com.bangkit.jetbukuku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: BookRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Book>>>
        get() = _uiState

    fun getAddedReadBooks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                repository.getAddedReadBooks()
                    .collect { books ->
                        if (books.isNotEmpty()) {
                            _uiState.value = UiState.Success(books)
                        } else {
                            _uiState.value = UiState.Error("No read books found")
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun updateReadBook(bookId: Long) {
        viewModelScope.launch {
            repository.updateReadBook(bookId)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedReadBooks()
                    }
                }
        }
    }
}