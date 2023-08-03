package com.lucasprioste.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.lucasprioste.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): Character
}