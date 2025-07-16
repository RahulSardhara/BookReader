package com.rahul.bookreader.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahul.bookreader.screens.home.ReaderHomeScreen
import com.rahul.bookreader.screens.login.ReaderLoginScreen
import com.rahul.bookreader.screens.search.ReaderSearchScreen
import com.rahul.bookreader.screens.splash.ReaderSplashScreen

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

    }
}