package com.example.jetbooks.di

import com.example.jetbooks.data.BookRepository

object Injection {
    fun provideRepository() : BookRepository {
        return BookRepository.getInstance()
    }
}