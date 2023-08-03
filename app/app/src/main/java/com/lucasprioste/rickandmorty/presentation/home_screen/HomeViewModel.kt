package com.lucasprioste.rickandmorty.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.mapper.toCharacter
import com.lucasprioste.rickandmorty.domain.use_case.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCharacters: GetCharacters
): ViewModel() {

    val charactersPagingFlow = getCharacters
        .getAll()
        .cachedIn(viewModelScope)
}