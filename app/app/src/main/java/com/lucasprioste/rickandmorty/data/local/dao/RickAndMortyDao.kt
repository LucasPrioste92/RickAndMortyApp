package com.lucasprioste.rickandmorty.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lucasprioste.rickandmorty.data.local.entity.CharacterEntity

@Dao
interface RickAndMortyDao {

    @Upsert
    suspend fun upsertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity WHERE LOWER(name) LIKE LOWER(:searchName) OR LOWER(status) LIKE LOWER(:status) OR LOWER(gender) LIKE LOWER(:gender)")
    fun pagingSource(searchName: String, status: String, gender: String): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id == :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("DELETE FROM characterentity")
    suspend fun clearAll()
}