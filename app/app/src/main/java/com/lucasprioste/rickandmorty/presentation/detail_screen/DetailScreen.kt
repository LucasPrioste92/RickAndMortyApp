package com.lucasprioste.rickandmorty.presentation.detail_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lucasprioste.rickandmorty.R
import com.lucasprioste.rickandmorty.core.Const.CardWidth
import com.lucasprioste.rickandmorty.core.Const.HeightCard
import com.lucasprioste.rickandmorty.core.Const.MarginHor
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.model.Gender
import com.lucasprioste.rickandmorty.domain.model.Status
import com.lucasprioste.rickandmorty.presentation.core.ui.theme.*
import com.lucasprioste.rickandmorty.presentation.detail_screen.components.InformationItem

@Composable
fun DetailScreen(
    character: Character,
    navController: NavController
){
    val colorStatus: Color = when(character.status){
        is Status.Alive -> Alive
        is Status.Dead -> Dead
        is Status.Unknown -> Unknown
    }

    val genderIcon: Int = when(character.gender){
        Gender.Female -> R.drawable.female_icon
        Gender.Genderless -> R.drawable.genderless_icon
        Gender.Male -> R.drawable.male_icon
        Gender.Unknown -> R.drawable.genderless_icon
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(shape = RoundedCornerShape(bottomStart = 35.dp)),
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxSize(),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
            )
            Card(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
                    .size(42.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                shape = RoundedCornerShape(25.dp),
                backgroundColor = White,
                elevation = 2.dp
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_icon),
                    contentDescription = stringResource(id = R.string.arrow_back),
                    modifier = Modifier
                        .size(35.dp)
                        .padding(5.dp)
                        .align(Alignment.Center),
                    tint = Black
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = MarginHor)
                .padding(top = 15.dp)
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background_detail),
                    contentScale = ContentScale.Fit,
                    alpha = 0.1f
                )
        ) {
            Text(text = character.name, style = MaterialTheme.typography.h1)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.circle_icon),
                    contentDescription = stringResource(id = R.string.status_life),
                    tint = colorStatus
                )
                Text(
                    text = stringResource(id = character.status.resourceId).replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.h4,
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                columns = GridCells.Adaptive(CardWidth)
            ) {
                item {
                    InformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(HeightCard),
                        icon = R.drawable.born_icon,
                        iconDescription = R.string.icon_description,
                        textValue = character.origin
                    )
                }
                item {
                    InformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(HeightCard),
                        icon = R.drawable.location_icon,
                        iconDescription = R.string.location_icon,
                        textValue = character.location
                    )
                }
                item {
                    InformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(HeightCard),
                        icon = R.drawable.specie_icon,
                        iconDescription = R.string.icon_description,
                        textValue = character.species
                    )
                }
                item {
                    InformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(HeightCard),
                        icon = genderIcon,
                        iconDescription = R.string.icon_description,
                        textValue = stringResource(id = character.gender.resourceId)
                    )
                }
            }
        }
    }
}