package com.lucasprioste.rickandmorty.domain.use_case

import androidx.paging.PagingData
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow

class GetCharacters constructor(
    private val repository: RickAndMortyRepository
) {
    fun getAll(): Flow<PagingData<Character>> {
        return repository.getCharacters()
    }
    suspend fun getById(id: Int): Character{
        return repository.getCharacterById(id)
    }
}