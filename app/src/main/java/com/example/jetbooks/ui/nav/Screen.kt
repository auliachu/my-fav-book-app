package com.example.jetbooks.ui.nav

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object About : Screen("about")
    data object DetailBook : Screen("detail/{bookId}") {
        fun createRoute(bookId : Int) = "detail/$bookId"
    }
}