package com.bangkit.jetbukuku.ui.screen.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.jetbukuku.R
import com.bangkit.jetbukuku.di.Injection
import com.bangkit.jetbukuku.model.Book
import com.bangkit.jetbukuku.ui.common.UiState
import com.bangkit.jetbukuku.ui.components.LibraryButton
import com.bangkit.jetbukuku.ui.components.LibraryItem
import com.bangkit.jetbukuku.ui.screen.ViewModelFactory

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onLibraryButtonClicked: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                LibraryBook(
                    uiState.data,
                    onUpdateReadBook = { bookId, book ->
                        viewModel.updateReadBook(bookId, book)
                    },
                    onLibraryButtonClicked = onLibraryButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryBook(
    book: Book,
    onUpdateReadBook: (Long, Book) -> Unit,
    onLibraryButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        book.title
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_library),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(items = listOf(book), key = { book.id }) {
                LibraryItem(
                    image = book.image,
                    title = book.title,
                    author = book.author,
                    year = book.year,
                )
                Divider()
            }
        }
        LibraryButton(
            text = stringResource(R.string.share_book),
            enabled = book != null,
            onClick = {
                onUpdateReadBook(book.id, book)
                onLibraryButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}