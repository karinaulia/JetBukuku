package com.bangkit.jetbukuku.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.jetbukuku.R
import com.bangkit.jetbukuku.di.Injection
import com.bangkit.jetbukuku.ui.common.UiState
import com.bangkit.jetbukuku.ui.components.LibraryButton
import com.bangkit.jetbukuku.ui.screen.ViewModelFactory
import com.bangkit.jetbukuku.ui.theme.JetBukukuTheme

@Composable
fun DetailScreen(
    bookId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToLibrary: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getBookById(bookId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailBook(
                    data.image,
                    data.title,
                    data.author,
                    data.year,
                    data.desc,
                    onBackClick = navigateBack,
                    onAddToLibrary = {
                        viewModel.addToLibrary(data)
                        navigateToLibrary()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailBook(
    @DrawableRes image: Int,
    title: String,
    author: String,
    year: String,
    desc: String,
    onBackClick: () -> Unit,
    onAddToLibrary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ){
        Box {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
            Icon(
               imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.back),
                modifier = modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.title),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = title,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag("BookList")
            )
            Text(
                text = stringResource(R.string.author),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = author,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(R.string.year),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = year,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, bottom = 16.dp)
                    .height(1.dp)
                    .background(Color.LightGray)
            )
            Text(
                text = desc,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LibraryButton(
                text = stringResource(R.string.add_to_library),
                onClick = {
                    onAddToLibrary()
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailBookPreview(){
    JetBukukuTheme {
        DetailBook(
            image = R.drawable.book_5,
            title = "LUMPU",
            author = "Tere Liye",
            year = "2021",
            desc = "Yes! Akhirnya, Raib, Seli, dan Ali kembali bertualang. Kalian sudah kangen dengan trio ini? Misi mereka adalah menyelamatkan Miss Selena, guru matematika mereka. Tapi, apakah semua berjalan mudah? Siapa yang bersedia membantu mereka? Kali ini, si genius Ali memutuskan meminta bantuan dari sosok yang tidak terduga, karena musuh dari musuh adalah teman. Apakah Raib bisa melupakan masa lalu itu dengan memaafkan Miss Selena? Bagaimana dengan Tazk? Apakah Raib bisa bertemu lagi dengan ayahnya, atau itu masih menjadi misteri? Bagaimana dengan jejak ekspedisi Klan Aldebaran 40.000 tahun lalu? Benda apa saja yang ditinggalkan oleh perjalanan besar tersebut? Pertarungan panjang telah menunggu mereka. Dan lawan mereka adalah Lumpu, petarung yang memiliki teknik unik, yaitu melumpuhkan kekuatan lawan. Itu teknik yang amat menakutkan, karena Lumpu bisa menghabisi teknik bertarung. Jangan-jangan… Siapa di antara Raib, Seli, dan Ali yang akan kehilangan kekuatan di dunia paralel?",
            onBackClick = { },
            onAddToLibrary = { })
    }
}