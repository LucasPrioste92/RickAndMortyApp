package com.lucasprioste.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RickAndMortyDao {

    @Upsert
    suspend fun upsertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id == :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("DELETE FROM characterentity")
    suspend fun clearAll()
}