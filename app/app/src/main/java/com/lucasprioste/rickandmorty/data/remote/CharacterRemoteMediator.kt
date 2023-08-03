package com.lucasprioste.rickandmorty.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lucasprioste.rickandmorty.data.local.RickAndMortyDatabase
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.mapper.toCharacterEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val rickAndMortyApi: RickAndMortyApi,
    private val rickAndMortyDb: RickAndMortyDatabase
): RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null)
                        1
                    else{
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val characters = rickAndMortyApi.getCharacters(
                page = loadKey,
            )

            rickAndMortyDb.withTransaction {
                if (loadType == LoadType.REFRESH){
                    rickAndMortyDb.dao.clearAll()
                }
                val charactersEntities = characters.results.map { it.toCharacterEntity() }
                rickAndMortyDb.dao.upsertAllCharacters(charactersEntities)

                MediatorResult.Success(
                    endOfPaginationReached = characters.info.next == null
                )
            }
        }   catch (e: IOException){
            MediatorResult.Error(e)
        }     catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}