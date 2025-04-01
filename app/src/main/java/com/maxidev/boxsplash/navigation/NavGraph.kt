package com.maxidev.boxsplash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maxidev.boxsplash.presentation.detail.DetailPhotoView
import com.maxidev.boxsplash.presentation.home.HomeView
import com.maxidev.boxsplash.presentation.search.SearchView
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            HomeView(
                navController = navController
            )
        }

        composable<SearchScreen> {
            SearchView(
                navController = navController
            )
        }

        composable<PhotoDetailScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<PhotoDetailScreen>()

            DetailPhotoView(
                id = args.id,
                navController = navController
            )
        }
    }
}

@Serializable data object HomeScreen
@Serializable data object SearchScreen
@Serializable data class PhotoDetailScreen(val id: String)