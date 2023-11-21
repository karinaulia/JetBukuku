package com.bangkit.jetbukuku.data

import com.bangkit.jetbukuku.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BookRepository {

    private val books = mutableListOf<Book>()

    fun getAllBooks(): Flow<List<Book>> {
        return flowOf(books)
    }

    fun getReadBookById(bookId: Long): Book {
        return books.first {
            it.id == bookId
        }
    }

    fun updateReadBook(bookId: Long): Flow<Boolean> {
        val index = books.indexOfFirst { it.id == bookId }
        val result = if (index >= 0) {
            val book = books[index]
            books[index] =
                book.copy()
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedReadBooks(): Flow<List<Book>> {
        return getAllBooks()
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