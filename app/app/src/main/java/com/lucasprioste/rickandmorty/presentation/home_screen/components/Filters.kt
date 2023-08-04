package com.lucasprioste.rickandmorty.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucasprioste.rickandmorty.R
import com.lucasprioste.rickandmorty.domain.model.FilterInfo
import com.lucasprioste.rickandmorty.presentation.core.ui.theme.Black
import com.lucasprioste.rickandmorty.presentation.core.ui.theme.White
import com.lucasprioste.rickandmorty.presentation.home_screen.HomeContract

@Composable
fun Filters(
    modifier: Modifier,
    filterStatus: List<FilterInfo>,
    filterGender: List<FilterInfo>,
    onEvent: (event: HomeContract.HomeEvent) -> Unit,
){
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                text = stringResource(id = R.string.filters),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                items(filterStatus){
                    val color = if (it.isSelected) White else Color.Transparent
                    Spacer(modifier = Modifier.width(3.dp))
                    FilterItem(
                        modifier = Modifier
                            .height(35.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(color = color, shape = RoundedCornerShape(7.dp))
                            .border(width = 2.dp, color = Black, shape = RoundedCornerShape(7.dp))
                            .clickable {
                                onEvent(HomeContract.HomeEvent.OnChangeFilterStatus(it))
                            },
                        filter = it
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                items(filterGender){
                    val color = if (it.isSelected) White else Color.Transparent
                    Spacer(modifier = Modifier.width(3.dp))
                    FilterItem(
                        modifier = Modifier
                            .height(35.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(color = color, shape = RoundedCornerShape(7.dp))
                            .border(width = 2.dp, color = Black, shape = RoundedCornerShape(7.dp))
                            .clickable {
                                onEvent(HomeContract.HomeEvent.OnChangeFilterGender(it))
                            },
                        filter = it
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
        }
    }
}