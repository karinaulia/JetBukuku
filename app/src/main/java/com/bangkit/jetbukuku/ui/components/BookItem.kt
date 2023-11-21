package com.bangkit.jetbukuku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.jetbukuku.R
import com.bangkit.jetbukuku.ui.theme.JetBukukuTheme
import com.bangkit.jetbukuku.ui.theme.Shapes

@Composable
fun BookItem(
    image: Int,
    title: String,
    author: String,
    year: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(240.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = author,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = year,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Light
            ),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BookItemPreview() {
    JetBukukuTheme {
        BookItem(image = R.drawable.book_5, title = "LUMPU", author = "Tere Liye", year = "2021")
    }
}