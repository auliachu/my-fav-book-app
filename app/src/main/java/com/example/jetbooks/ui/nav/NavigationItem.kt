package com.example.jetbooks.ui.nav

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val title : String,
    val icon : ImageVector,
    val screen : Screen
)
