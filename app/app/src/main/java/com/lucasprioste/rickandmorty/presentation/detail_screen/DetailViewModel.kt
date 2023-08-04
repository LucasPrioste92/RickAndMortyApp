package com.lucasprioste.rickandmorty.presentation.detail_screen

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasprioste.rickandmorty.R
import com.lucasprioste.rickandmorty.core.Const.NavArgCharacterId
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.model.Gender
import com.lucasprioste.rickandmorty.domain.model.InformationItemData
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import com.lucasprioste.rickandmorty.domain.use_case.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getCharacters: GetCharacters,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _character = MutableStateFlow(Character())
    val character = _character.asStateFlow()

    private val _informationItems = MutableStateFlow(listOf<InformationItemData>())
    val informationItems = _informationItems.asStateFlow()

    init {
        savedStateHandle.get<Int>(NavArgCharacterId)?.let { id ->
            viewModelScope.launch {
                _character.update { getCharacters.getById(id) }
                _informationItems.update {
                    listOf(
                        InformationItemData(
                            icon = R.drawable.born_icon,
                            iconDescription = R.string.icon_description,
                            textValue = character.value.origin
                        ),
                        InformationItemData(
                            icon = R.drawable.location_icon,
                            iconDescription = R.string.location_icon,
                            textValue = character.value.location
                        ),
                        InformationItemData(
                            icon = R.drawable.specie_icon,
                            iconDescription = R.string.icon_description,
                            textValue = character.value.species
                        ),
                        InformationItemData(
                            icon = genderIcon(character.value.gender),
                            iconDescription = R.string.icon_description,
                            textValue = character.value.gender.name,
                        ),
                    )
                }
            }
        }
    }

    private fun genderIcon(gender: Gender): Int{
        return when(gender){
            Gender.Female -> R.drawable.female_icon
            Gender.Genderless -> R.drawable.genderless_icon
            Gender.Male -> R.drawable.male_icon
            Gender.Unknown -> R.drawable.genderless_icon
        }
    }
}