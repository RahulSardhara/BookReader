package com.rahul.bookreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rahul.bookreader.screens.details.ReaderBookDetailsScreen
import com.rahul.bookreader.screens.home.ReaderHomeScreen
import com.rahul.bookreader.screens.login.ReaderLoginScreen
import com.rahul.bookreader.screens.search.ReaderSearchScreen
import com.rahul.bookreader.screens.splash.ReaderSplashScreen
import com.rahul.bookreader.screens.stats.ReaderStatsScreen
import com.rahul.bookreader.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){

        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.HomeScreen.name){
            ReaderHomeScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.StatsScreen.name){
            ReaderStatsScreen(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            ReaderSearchScreen(navController = navController)
        }

        composable(ReaderScreens.DetailsScreen.name + "/{bookItemId}", arguments = listOf(navArgument("bookItemId") {
            type = androidx.navigation.NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId")?.let { bookItemId ->
                ReaderBookDetailsScreen(navController = navController, bookItemId = bookItemId)
            }
        }

        composable(ReaderScreens.UpdateScreen.name + "/{bookItemId}", arguments = listOf(navArgument("bookItemId") {
            type = androidx.navigation.NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId")?.let { bookItemId ->
                ReaderUpdateScreen(navController = navController, bookItemId = bookItemId)
            }
        }


    }
}