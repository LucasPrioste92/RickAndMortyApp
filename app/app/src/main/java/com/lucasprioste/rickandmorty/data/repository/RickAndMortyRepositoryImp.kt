package com.lucasprioste.rickandmorty.data.repository

import androidx.paging.*
import com.lucasprioste.rickandmorty.data.local.RickAndMortyDatabase
import com.lucasprioste.rickandmorty.data.mapper.toCharacter
import com.lucasprioste.rickandmorty.data.pagination.CharacterRemoteMediator
import com.lucasprioste.rickandmorty.data.remote.RickAndMortyApi
import com.lucasprioste.rickandmorty.domain.model.Character
import com.lucasprioste.rickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.*

class RickAndMortyRepositoryImp(
    private val api: RickAndMortyApi,
    private val db: RickAndMortyDatabase
): RickAndMortyRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(searchName: String, status: String?, gender: String?) = Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(
                rickAndMortyApi = api,
                rickAndMortyDb = db,
                searchName = searchName,
                genderRequest = gender,
                statusRequest = status
            ),
        ){
            db.rickAndMortyDao.pagingSource(
                searchName = "%$searchName%",
                gender = gender ?: "",
                status = status ?: ""
            )
        }.flow.map { it.map { it.toCharacter() } }

    override suspend fun getCharacterById(id: Int): Character {
        return db.rickAndMortyDao.getCharacterById(id).toCharacter()
    }
}