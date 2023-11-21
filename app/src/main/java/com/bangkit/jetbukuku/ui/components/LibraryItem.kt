package com.bangkit.jetbukuku.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun LibraryItem(
    image: Int,
    title: String,
    author: String,
    year: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
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
}

@Composable
@Preview(showBackground = true)
fun LibraryItemPreview() {
    JetBukukuTheme {
        LibraryItem(image = R.drawable.book_5, title = "LUMPU", author = "Tere Liye", year = "2021")
    }
}