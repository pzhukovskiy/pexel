package com.example.fetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.fetch.compose.home.HomeScreen
import com.example.fetch.compose.photodetail.PhotoDetailScreen
import com.example.fetch.compose.photolist.PhotoListScreen
import com.example.fetch.compose.photolistfromdatabase.PhotoListFromDatabaseScreen
import com.example.fetch.database.PhotoDatabase
import com.example.fetch.navigation.NavigationItem
import com.example.fetch.repository.PhotoRepositoryImplementation
import com.example.fetch.ui.theme.FetchTheme
import com.example.fetch.viewmodels.PhotoViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PhotoDatabase::class.java,
            "photos.db"
        ).build()
    }

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
        val viewModel = PhotoViewModel(PhotoRepositoryImplementation(), db.dao)
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route
        ) {

            //bottom bar
            composable(NavigationItem.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }

            //image details
            composable(
                "${NavigationItem.PhotoDetail.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val photoId = backStackEntry.arguments?.getInt("id")
                val selectedPhoto = viewModel.photoList.find { it.id == photoId }
                selectedPhoto?.let {
                    PhotoDetailScreen(
                        onEvent = viewModel::onEvent,
                        photo = it,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            //image list from database
            composable(
                NavigationItem.PhotoListFromDatabase.route
            ) {
                val state by viewModel.state.collectAsState()
                PhotoListFromDatabaseScreen(
                    navController = navController,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}