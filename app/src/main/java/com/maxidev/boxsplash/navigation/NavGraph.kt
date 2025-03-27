package com.maxidev.boxsplash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maxidev.boxsplash.presentation.home.HomeView
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
    }
}

@Serializable data object HomeScreen
@Serializable data object SearchScreen