package com.lucasprioste.rickandmorty.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.lucasprioste.rickandmorty.data.local.RickAndMortyDatabase
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import com.lucasprioste.rickandmorty.data.local.entity.RickAndMortyRemoteKeys
import com.lucasprioste.rickandmorty.data.mapper.toCharacterEntity
import com.lucasprioste.rickandmorty.data.remote.RickAndMortyApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val searchName: String,
    private val statusRequest: String?,
    private val genderRequest: String?,
    private val rickAndMortyApi: RickAndMortyApi,
    private val rickAndMortyDb: RickAndMortyDatabase,
): RemoteMediator<Int, CharacterEntity>() {

    private val rickAndMortyDao = rickAndMortyDb.rickAndMortyDao
    private val remoteKeys = rickAndMortyDb.remoteKeysDao

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val currentPage = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val queryName = searchName.ifBlank { null }

            val characters = rickAndMortyApi.getCharacters(
                page = currentPage,
                name = queryName,
                status = statusRequest,
                gender = genderRequest,
            )
            val endOfPaginationReached = characters.info.next == null

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            rickAndMortyDb.withTransaction {
                if (loadType == LoadType.REFRESH){
                    rickAndMortyDao.clearAll()
                    remoteKeys.clearAll()
                }

                val charactersEntities = characters.results.map { it.toCharacterEntity() }
                rickAndMortyDao.upsertAllCharacters(charactersEntities)

                val keys = charactersEntities.map { character ->
                    RickAndMortyRemoteKeys(
                        id = character.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                    )
                }
                remoteKeys.upsertAllRemoteKeys(remoteKeys = keys)
                MediatorResult.Success(
                    endOfPaginationReached = endOfPaginationReached
                )
            }
        } catch (e: IOException){
            MediatorResult.Error(e)
        } catch (e: HttpException){
            MediatorResult.Error(e)
        } catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterEntity>
    ): RickAndMortyRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { characterEntity ->
                remoteKeys.getRemoteKeys(id = characterEntity.id)
            }
    }
}