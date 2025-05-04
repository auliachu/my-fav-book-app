package com.example.jetbooks.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetbooks.ViewModelFactory
import com.example.jetbooks.di.Injection
import com.example.jetbooks.model.Book
import com.example.jetbooks.ui.common.UiState
import com.example.jetbooks.ui.components.BookListItem
import com.example.jetbooks.ui.components.CharHeader
import com.example.jetbooks.ui.components.ScrollToTopButton
import kotlinx.coroutines.launch

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState->
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                Home(
                    listBook = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Error")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    listBook: List<Book>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton : Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(modifier= modifier){
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
        ) {
            listBook.groupBy { it.name.first() }.forEach{ (initial, books)->
                stickyHeader {
                    CharHeader(initial)
                }
                items(books, key = {it.id}) { book ->
                    BookListItem(
                        id = book.id,
                        name = book.name,
                        photoUrl = book.photoUrl,
                        desc = book.desc,
                        modifier = Modifier
                            .fillMaxWidth(),
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}