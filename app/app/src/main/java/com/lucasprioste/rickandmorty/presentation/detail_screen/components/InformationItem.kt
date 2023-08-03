package com.lucasprioste.rickandmorty.presentation.detail_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun InformationItem(
    modifier: Modifier = Modifier,
    icon: Int,
    iconDescription: Int,
    textValue: String,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                width = 3.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(5.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = iconDescription)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = textValue.replaceFirstChar { it.uppercase() },
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}