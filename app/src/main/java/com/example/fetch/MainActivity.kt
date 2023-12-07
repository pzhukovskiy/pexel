package com.example.fetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fetch.compose.home.HomeScreen
import com.example.fetch.compose.photodetail.ImageDetailScreen
import com.example.fetch.compose.photolist.ImageListScreen
import com.example.fetch.navigation.NavigationItem
import com.example.fetch.repository.PhotoRepositoryImplementation
import com.example.fetch.ui.theme.FetchTheme
import com.example.fetch.viewmodels.PhotoViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchTheme {
                NavGraph()
            }
        }
    }

    @Composable
    fun NavGraph(
        navController: NavHostController = rememberNavController()
    ) {
        val viewModel = PhotoViewModel(PhotoRepositoryImplementation())
        NavHost(
            navController = navController,
            startDestination = NavigationItem.ImageList.route
        ) {
            composable(NavigationItem.Home.route) {
                HomeScreen()//bottom bar
            }
            composable(NavigationItem.ImageList.route) {
                ImageListScreen(
                    viewModel = viewModel,
                ) { photo ->
                    navController.navigate("${NavigationItem.ImageDetail.route}/${photo.id}")
                }
            }
            composable(
                "${NavigationItem.ImageDetail.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val photoId = backStackEntry.arguments?.getInt("id")
                val selectedPhoto = viewModel.photoList.find { it.id == photoId }
                selectedPhoto?.let {
                    ImageDetailScreen(photo = it) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}