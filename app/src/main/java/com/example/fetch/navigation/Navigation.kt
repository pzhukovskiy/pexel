package com.example.fetch.navigation

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem("Home")
    data object PhotoList : NavigationItem("PhotoListScreen")
    data object PhotoDetail : NavigationItem("PhotoDetailScreen")
    data object PhotoListFromDatabase : NavigationItem("PhotoListFromDatabaseScreen")
}