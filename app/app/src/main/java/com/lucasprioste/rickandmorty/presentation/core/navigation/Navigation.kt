package com.lucasprioste.rickandmorty.presentation.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.lucasprioste.rickandmorty.core.Const.NavArgCharacterId
import com.lucasprioste.rickandmorty.presentation.detail_screen.DetailScreen
import com.lucasprioste.rickandmorty.presentation.detail_screen.DetailViewModel
import com.lucasprioste.rickandmorty.presentation.home_screen.HomeScreen
import com.lucasprioste.rickandmorty.presentation.home_screen.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Route.HomeScreen.route,
    ) {
        composable(
            route = Route.HomeScreen.route,
        ) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val characters = viewModel.characters.collectAsLazyPagingItems()
            val filterState = viewModel.filterState.collectAsState().value

            HomeScreen(
                characters = characters,
                searchInput = filterState.searchName,
                filterGender = filterState.filterGender,
                filterStatus = filterState.filterStatus,
                onEvent = viewModel::onEvent,
                navController = navController
            )
        }
        composable(
            route = Route.DetailScreen.route + "?$NavArgCharacterId={$NavArgCharacterId}",
            arguments = listOf(
                navArgument(
                    name = NavArgCharacterId
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Companion.Left,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Companion.Right,
                    animationSpec = tween(600)
                )
            }
        ) {
            val viewModel = hiltViewModel<DetailViewModel>()
            val character = viewModel.character.collectAsState().value
            val informationItems = viewModel.informationItems.collectAsState().value
            DetailScreen(
                character = character,
                informationItems = informationItems,
                navController = navController
            )
        }
    }
}