package com.bangkit.jetbukuku.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jetbukuku.data.BookRepository
import com.bangkit.jetbukuku.model.Book
import com.bangkit.jetbukuku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: BookRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Book>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Book>>
        get() = _uiState

    fun getBookById(bookId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getReadBookById(bookId))
        }
    }

    fun addToLibrary(book: Book) {
        viewModelScope.launch {
            repository.updateReadBook(book.id)
        }
    }
}