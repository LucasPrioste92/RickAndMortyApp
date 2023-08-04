package com.lucasprioste.rickandmorty.presentation.home_screen


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lucasprioste.rickandmorty.R
import com.lucasprioste.rickandmorty.core.Const.CardWidth
import com.lucasprioste.rickandmorty.core.Const.MarginHor
import com.lucasprioste.rickandmorty.core.Const.NavArgCharacterId
import com.lucasprioste.rickandmorty.core.shimmerEffect
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.model.FilterInfo
import com.lucasprioste.rickandmorty.presentation.core.navigation.Route
import com.lucasprioste.rickandmorty.presentation.home_screen.HomeContract.HomeEvent
import com.lucasprioste.rickandmorty.presentation.home_screen.components.CharacterItem
import com.lucasprioste.rickandmorty.presentation.home_screen.components.Filters

@Composable
fun HomeScreen(
    characters: LazyPagingItems<Character>,
    searchInput: String,
    filterStatus: List<FilterInfo>,
    filterGender: List<FilterInfo>,
    onEvent: (event: HomeEvent) -> Unit,
    navController: NavController,
){
    var showFilters by remember {
        mutableStateOf(false)
    }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MarginHor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = searchInput,
                onValueChange = {
                    onEvent(
                        HomeEvent.OnSearch(it)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.onSurface
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(5.dp)
                    ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 18.sp
                    )
                },
                maxLines = 1,
                singleLine = true
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.menu_icon),
                contentDescription = stringResource(id = R.string.menu_icon),
                modifier = Modifier
                    .size(45.dp)
                    .clickable {
                        showFilters = !showFilters
                    }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (characters.loadState.refresh is LoadState.Loading){
                Column(
                    modifier = Modifier
                        .padding(horizontal = MarginHor),
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
                this@Column.AnimatedVisibility(visible = characters.itemCount == 0) {
                    Text(
                        text = stringResource(id = R.string.no_match_data),
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier
                            .padding(horizontal = MarginHor, vertical = 24.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MarginHor)
                        .padding(bottom = 10.dp),
                    columns = GridCells.Adaptive(CardWidth),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(characters.itemCount) {
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
                        if (characters.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            this@Column.AnimatedVisibility(
                visible = showFilters,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
            ){
                Filters(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()

                                val (_,y) = dragAmount
                                when {
                                    y > 0 -> { showFilters = false }
                                }

                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        },
                    filterStatus = filterStatus,
                    filterGender = filterGender,
                    onEvent = onEvent
                )
            }
        }
    }
}