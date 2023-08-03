package com.lucasprioste.rickandmorty.presentation.detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasprioste.rickandmorty.core.Const.NavArgCharacterId
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    repository: RickAndMortyRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _character = MutableStateFlow(Character())
    val character = _character.asStateFlow()

    init {
        savedStateHandle.get<Int>(NavArgCharacterId)?.let { id ->
            viewModelScope.launch {
                _character.update { repository.getCharacterById(id) }
            }
        }
    }
}