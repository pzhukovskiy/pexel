package com.example.fetch.utilities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import com.example.fetch.data.BottomNavigationItem
import com.example.fetch.navigation.NavigationItem

val BottomBarItem = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        route = NavigationItem.Home.route
    ),
//    BottomNavigationItem(
//        title = "Chat",
//        selectedIcon = Icons.Filled.Email,
//        unselectedIcon = Icons.Outlined.Email,
//        hasNews = false,
//        badgeCount = 45,
//        route = ""
//    ),
    BottomNavigationItem(
        title = "Favorite",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.Favorite,
        hasNews = true,
        route = NavigationItem.PhotoListFromDatabase.route
    ),
)