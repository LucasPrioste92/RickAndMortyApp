package com.lucasprioste.rickandmorty.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.route,
    ) {
        composable(route = Route.HomeScreen.route) {

        }
        composable(route = Route.DetailScreen.route) {

        }
    }
}