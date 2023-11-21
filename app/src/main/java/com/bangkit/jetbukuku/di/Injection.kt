package com.bangkit.jetbukuku.di

import com.bangkit.jetbukuku.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}