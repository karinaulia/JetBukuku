package com.bangkit.jetbukuku.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jetbukuku.data.BookRepository
import com.bangkit.jetbukuku.model.Book
import com.bangkit.jetbukuku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BookRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Book>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {libraryBook ->
                    _uiState.value = UiState.Success(libraryBook)
                }
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        val searchResult = repository.searchBooks(_query.value).sortedBy { it.title }
        _uiState.value = UiState.Success(searchResult)
    }

}