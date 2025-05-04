package com.example.jetbooks.data

import com.example.jetbooks.model.Book
import com.example.jetbooks.model.BooksData

class BookRepository {
    fun getBooks(): List<Book> {
        return BooksData.books
    }

    private val dummyBook = mutableListOf<Book>()

    init {
        if (dummyBook.isEmpty()){
            BooksData.books.forEach{
                dummyBook.add(it)
            }
        }
    }

    fun getBookById(bookId: Int): Book {
        return dummyBook.first{
            it.id == bookId
        }
    }


    companion object {
        @Volatile
        private var instance : BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this){
                BookRepository().apply {
                    instance = this
                }
            }
    }
}