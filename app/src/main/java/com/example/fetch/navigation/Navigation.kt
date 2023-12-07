package com.example.fetch.navigation

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem("Home")
    data object ImageList : NavigationItem("ImageListScreen")
    data object ImageDetail : NavigationItem("ImageDetailScreen")
}