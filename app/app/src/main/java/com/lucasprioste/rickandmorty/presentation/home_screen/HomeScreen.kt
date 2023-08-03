package com.lucasprioste.rickandmorty.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lucasprioste.rickandmorty.core.Const.CardWidth
import com.lucasprioste.rickandmorty.core.Const.NavArgCharacterId
import com.lucasprioste.rickandmorty.core.shimmerEffect
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.presentation.core.navigation.Route
import com.lucasprioste.rickandmorty.presentation.home_screen.components.CharacterItem

@Composable
fun HomeScreen(
    characters: LazyPagingItems<Character>,
    navController: NavController,
){
    val context = LocalContext.current
    LaunchedEffect(key1 = characters.loadState){
        if (characters.loadState.refresh is LoadState.Error){
            Toast.makeText(
                context,
                "Error: " + (characters.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (characters.loadState.refresh is LoadState.Loading){
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    (1..2).map {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(CardWidth)
                                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                                    .shimmerEffect()
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(CardWidth)
                                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }else{
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(CardWidth),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    items(characters.itemCount){
                        characters[it]?.let { character ->
                            CharacterItem(
                                character = character,
                                modifier = Modifier
                                    .height(CardWidth)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController
                                            .navigate(Route.DetailScreen.route + "?$NavArgCharacterId=${character.id}") {
                                                launchSingleTop = true
                                            }
                                    }
                            )
                        }
                    }
                    item {
                        if(characters.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}