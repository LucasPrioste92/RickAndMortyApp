package com.lucasprioste.rickandmorty.presentation.core.navigation

sealed class Route(val route: String){
    object HomeScreen: Route("home_screen")
    object DetailScreen: Route("detail_screen")
}