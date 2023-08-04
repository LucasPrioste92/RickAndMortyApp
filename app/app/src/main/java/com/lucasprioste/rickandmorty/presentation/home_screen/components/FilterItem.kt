package com.lucasprioste.rickandmorty.presentation.home_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lucasprioste.rickandmorty.domain.model.FilterInfo
import com.lucasprioste.rickandmorty.presentation.core.ui.theme.Black

@Composable
fun FilterItem(
    modifier: Modifier,
    filter: FilterInfo,
){
    Box(modifier = modifier){
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .align(Alignment.Center)
                .wrapContentHeight(),
            text = stringResource(id = filter.resourceId).replaceFirstChar { it.uppercase() },
            color = Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }

}