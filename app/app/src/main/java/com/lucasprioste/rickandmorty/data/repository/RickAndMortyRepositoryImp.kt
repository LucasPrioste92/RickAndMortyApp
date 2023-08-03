package com.lucasprioste.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.lucasprioste.rickandmorty.data.local.RickAndMortyDatabase
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.mapper.toCharacter
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RickAndMortyRepositoryImp(
    private val pager: Pager<Int, CharacterEntity>,
    private val db: RickAndMortyDatabase
): RickAndMortyRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return pager.flow.map { pagingData ->
            pagingData.map { it.toCharacter() }
        }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return db.dao.getCharacterById(id).toCharacter()
    }
}