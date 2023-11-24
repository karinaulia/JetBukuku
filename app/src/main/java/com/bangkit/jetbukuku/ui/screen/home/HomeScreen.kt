package com.bangkit.jetbukuku.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.jetbukuku.R
import com.bangkit.jetbukuku.di.Injection
import com.bangkit.jetbukuku.model.Book
import com.bangkit.jetbukuku.ui.common.UiState
import com.bangkit.jetbukuku.ui.components.BookItem
import com.bangkit.jetbukuku.ui.screen.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooks()
            }
            is UiState.Success -> {
                HomeContent(
                    book = uiState.data,
                    modifier = modifier,
                    viewModel = viewModel,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarContent(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(id = R.string.search_book))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {

    }
}

@Composable
fun HomeContent(
    book: List<Book>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    val gridState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        SearchBarContent(
            query = query,
            onQueryChange = { newQuery ->
                viewModel.search(newQuery)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier
        ) {
            items(book) { data ->
                BookItem(
                    image = data.image,
                    title = data.title,
                    author = data.author,
                    year = data.year,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}