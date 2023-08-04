package com.lucasprioste.rickandmorty.domain.use_case

import androidx.paging.PagingData
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow

class GetCharacters constructor(
    private val repository: RickAndMortyRepository
) {
    fun getAll(searchName: String, status: String?=null, gender: String?=null): Flow<PagingData<Character>> {
        return repository.getCharacters(searchName = searchName, status = status, gender = gender)
    }
    suspend fun getById(id: Int): Character{
        return repository.getCharacterById(id = id)
    }
}