package com.example.jetbooks

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetbooks.data.BookRepository
import com.example.jetbooks.model.Book
import com.example.jetbooks.ui.screen.detail.DetailBookViewModel
import com.example.jetbooks.ui.screen.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetBooksViewModel(private val repository: BookRepository) : ViewModel() {
    private val _groupedBooks = MutableStateFlow(
        repository.getBooks()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedBooks : StateFlow<Map<Char, List<Book>>> get() = _groupedBooks

}

class ViewModelFactory(private val repository: BookRepository) :
        ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetBooksViewModel::class.java)) {
            return JetBooksViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
            return DetailBookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}