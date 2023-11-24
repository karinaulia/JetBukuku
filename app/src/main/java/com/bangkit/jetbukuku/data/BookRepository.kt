package com.bangkit.jetbukuku.data

import android.util.Log
import com.bangkit.jetbukuku.model.Book
import com.bangkit.jetbukuku.model.FakeBookDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BookRepository {

    private val books = mutableListOf<Book>()

    init {
        if (books.isEmpty()) {
            FakeBookDataSource.dummyBooks.forEach {
                books.add(it)
            }
        }
    }

    fun getAllBooks(): Flow<List<Book>> {
        return flowOf(books)
    }

    fun getReadBookById(bookId: Long): Book {
        Log.d("TAG", "$bookId")
        return books.first {
            it.id == bookId
        }
    }

    fun updateReadBook(bookId: Long): Flow<Boolean> {
        val index = books.indexOfFirst { it.id == bookId }
        val result = if (index >= 0) {
            val book = books[index]
            books[index] =
                book.copy(isRead = !book.isRead)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedReadBooks(): Flow<List<Book>> {
        return getAllBooks()
            .map { books ->
                books.filter { it.isRead }
            }
    }

    fun searchBooks(query: String): List<Book> {
        return books.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }
}