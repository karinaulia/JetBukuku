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
    private val _uiState: MutableStateFlow<UiState<Book>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Book>>
        get() = _uiState

    fun getAddedReadBooks(book: Book) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                repository.updateReadBook(book.id).collect { success ->
                    if (success) {
                        _uiState.value = UiState.Success(book)
                    } else {
                        _uiState.value = UiState.Error("Failed to update book in the library")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun updateReadBook(bookId: Long, book: Book) {
        viewModelScope.launch {
            repository.updateReadBook(bookId)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedReadBooks(book)
                    }
                }
        }
    }
}